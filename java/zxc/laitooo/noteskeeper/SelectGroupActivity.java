package zxc.laitooo.noteskeeper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectGroupActivity extends AppCompatActivity {

    private String TAG_TITLE = "groupname";
    private String TAG_LINK = "link_group";
    private String TAG_ADMIN = "id_admin";
    private String TAG_ID = "id_group";


    int ID,Admin_Id;
    String Title,Link;

    GroupNotesAdapter adapter;
    ArrayList<GroupNotes> list;

    EditText editText;
    ImageButton button;

    public static String Group_Notes_Url = "http://notes-keeper.000webhostapp.com/printGroupNotes.php";
    public static String CreateNote = "http://notes-keeper.000webhostapp.com/addGroupNote.php";
    static ProgressDialog progressDialog;

    RequestQueue mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_select_group);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        editText = (EditText)findViewById(R.id.enter_group_note);
        button = (ImageButton) findViewById(R.id.send_group_note);



        mRequest = Volley.newRequestQueue(getApplicationContext());

        progressDialog = new ProgressDialog(SelectGroupActivity.this);

        progressDialog.setTitle("Please wait...");
        progressDialog.show();
        new lgn().execute();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_select_group);
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        //list.add(new GroupNotes(22,22,44,"Welcome to the first group_notes every one","2019/1/6","Laitooo","pro"));
        //list.add(new GroupNotes(22,22,44,"Welcome to the first group_notes every one","2019/1/6","Laitooo","pro"));
        //list.add(new GroupNotes(22,22,44,"f***","2019/1/6","Laitooo Sama","pro"));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GroupNotesAdapter(list, getApplicationContext());
        recyclerView.setAdapter(adapter);

        Intent i = getIntent();
        Title = i.getStringExtra(TAG_TITLE);
        ID = i.getIntExtra(TAG_ID,0);
        Link = i.getStringExtra(TAG_LINK);
        /*dmin_Id = i.getIntExtra(TAG_ADMIN,0);
        /try {

        }catch (Exception e){
            Title = "Error" + e.getMessage();
            ID = 22;
            Link = "ee";
            Admin_Id = 222;
        }

        //Toast.makeText(getApplicationContext(),"id:"+ID+" title:"+Title+" admin_id:"
                +Admin_Id+" link:"+Link
                ,Toast.LENGTH_LONG).show();*/


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent in = new Intent(Intent.ACTION_SEND);
                    in.putExtra(Intent.EXTRA_TEXT,"Join this group on notes-keeper app using this linl "+ Link);
                    in.setType("text/plain");
                    startActivity(in);

            }
        });
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RetrofitActivity.class));
            }
        });*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"your note is empty",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("please wait.......");
                    progressDialog.show();
                    new save_note().execute();
                }
            }
        });
    }




    public class lgn extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            StringRequest request = new StringRequest(Request.Method.POST, Group_Notes_Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    try {
                        JSONObject no = new JSONObject(s);
                        JSONArray notes = no.getJSONArray("group_notes");
                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                        if (notes.length() == 0) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"no notes",Toast.LENGTH_LONG).show();
                            //empty_notes.setText("You dont have notes yet");
                        } else {

                            for (int i = notes.length() - 1; i >= 0; i--) {
                                JSONObject note = notes.getJSONObject(i);
                                list.add(new GroupNotes(
                                        note.getInt("id"),note.getInt("id_group"),note.getInt("id_user"),
                                        note.getString("content"),note.getString("date"),note.getString("username"),
                                        note.getString("profile")
                                ));
                            }
                            progressDialog.dismiss();
                            adapter.notifyDataSetChanged();
                        }
                        /*if (swipe.isRefreshing()) {
                            swipe.setRefreshing(false);
                        }
                        j = new MainActivity.nl();*/


                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        //if (swipe.isRefreshing()) {
                        //    swipe.setRefreshing(false);
                        //}
                        //empty.setText("Json Exception \n      try again");
                        Toast.makeText(getApplicationContext(),"sss " + e.getMessage(), Toast.LENGTH_LONG).show();
                        //j = new MainActivity.nl();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    //if (swipe.isRefreshing()) {
                    //    swipe.setRefreshing(false);
                    //}
                    String err = "connection error ";
                    try {
                        byte[] htmlBodyBytes = volleyError.networkResponse.data;
                        err+= new String(htmlBodyBytes);
                    } catch (NullPointerException e) {
                        err+= e.getMessage();
                    }
                    Toast.makeText(getApplicationContext(), err+
                            volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                    //empty_notes.setText("Connection Error \n      try again");
                    //j = new MainActivity.nl();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("id_group", String.valueOf(ID));
                    return parameters;
                }
            };
            r.setShouldCache(false);

            mRequest.add(request);
            return null;
        }
    }

    public class save_note extends AsyncTask{

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


    StringRequest r = new StringRequest(Request.Method.POST, CreateNote, new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            //j.setText(s);
            try {
                JSONObject object = new JSONObject(s);
                boolean error = object.getBoolean("error");
                if (!error){
                    Toast.makeText(getApplicationContext(),"Note saved successfully",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    //finish();
                    //done  = true;
                }else{
                    //done = false;
                    //new sa().execute();
                    //err.setText(object.getString("message"));
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"err"+object.getString("message")
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
            Toast.makeText(getApplicationContext(),"Connection error \n   please try again "
                    + volleyError.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }) {

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> parameters  = new HashMap<String, String>();
            ManageUser m = new ManageUser(getApplicationContext());
            parameters.put("id_user",String.valueOf(m.GetUserId()));
            parameters.put("id_group",String.valueOf(ID));
            parameters.put("content",editText.getText().toString());
            return parameters;
        }
    };
}
