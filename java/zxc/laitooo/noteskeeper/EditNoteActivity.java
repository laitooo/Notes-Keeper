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

public class EditNoteActivity extends AppCompatActivity {

    EditText ti,co;
    String stti,stco;

    private String TAG_TITLE = "edit_title";
    private String TAG_CONTENT = "edit_content";
    private String TAG_ID = "id";
    //boolean done ;

    private RequestQueue mRequest;

    ProgressDialog progressDialog;


    private int ID ;


    public static String Save_url = "http://notes-keeper.000webhostapp.com/editNoteApp.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ti = (EditText)findViewById(R.id.edit_note_title);
        co = (EditText)findViewById(R.id.edit_note_content);

        Intent i = getIntent();
        ti.setText(i.getStringExtra(TAG_TITLE));
        co.setText(i.getStringExtra(TAG_CONTENT));
        ID = i.getIntExtra(TAG_ID,0);


        mRequest = Volley.newRequestQueue(getApplicationContext());

        progressDialog = new ProgressDialog(EditNoteActivity.this);

        Button button = (Button)findViewById(R.id.note_button_edit);
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
            parameters.put("id",String.valueOf(ID));
            parameters.put("newTitle",stti);
            parameters.put("newContent",stco);
            return parameters;
        }
    };

}