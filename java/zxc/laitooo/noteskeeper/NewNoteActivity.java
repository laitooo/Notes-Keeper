package zxc.laitooo.noteskeeper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewNoteActivity extends AppCompatActivity {

    EditText ti,co;
    String stti,stco;

    //boolean done ;

    private RequestQueue mRequest;

    ProgressDialog progressDialog;


    public static String Save_url = "http://192.168.43.4:80/app/addNoteApp.php";

    //public static String Save_url = "http://notes-keeper.000webhostapp.com/addNoteApp.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ti = (EditText)findViewById(R.id.note_title);
        co = (EditText)findViewById(R.id.note_content);


        mRequest = Volley.newRequestQueue(getApplicationContext());

        progressDialog = new ProgressDialog(NewNoteActivity.this);

        Button button = (Button)findViewById(R.id.note_button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stti = ti.getText().toString();
                stco = co.getText().toString();
                //done = false;
                if (stco.equals("") && stti.equals("")) {
                    Toast.makeText(getApplicationContext(),"the note is empty",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Please wait...");
                    progressDialog.show();
                    new sa().execute();
                    //finish();
                }
            }
        });



    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_note, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.note_save){
            //Toast.makeText(getApplicationContext(),ti.getText().toString() + " saved",
            //        Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }*/

    public class sa extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            r.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            mRequest.add(r);

            return null;
        }

    }



    StringRequest r = new StringRequest(Request.Method.POST, Save_url, new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            //j.setText(s);
            try {
                JSONObject object = new JSONObject(s);
                boolean error = object.getBoolean("error");
                if (!error){
                    Toast.makeText(getApplicationContext(),"Note saved successfully",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    finish();
                    //done  = true;
                }else{
                    //done = false;
                    //new sa().execute();
                    //err.setText(object.getString("message"));
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),object.getString("message")
                            ,Toast.LENGTH_LONG).show();
                }
            }catch (JSONException e){
                //done = false;
                Toast.makeText(getApplicationContext(),"json exception : " +
                        e.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                //new sa().execute();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //done = false;
            //new sa().execute();
            Toast.makeText(getApplicationContext(),"Connection error \n   please try again ",
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }) {

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> parameters  = new HashMap<String, String>();
            ManageUser m = new ManageUser(getApplicationContext());
            parameters.put("id_user",String.valueOf(m.GetUserId()));
            if (stti.equals("")){
                parameters.put("title","no title");
            }else {
                parameters.put("title",stti);
            }
            if (stco.equals("")){
                parameters.put("content","no content");
            }else {
                parameters.put("content",stco);
            }
            return parameters;
        }
    };

}
