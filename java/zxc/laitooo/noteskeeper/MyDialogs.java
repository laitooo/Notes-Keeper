package zxc.laitooo.noteskeeper;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyDialogs extends DialogFragment {

    Context c;

    private RequestQueue mRequest;

    ProgressDialog progressDialog;

    EditText editText;
    private int delete_id;


    public static String TAG_DELETE_NOTE_EDIT = "delete in edit note";
    public static String TAG_NEW_GROUP_MAIN = "new group in main activity";
    private String TAG;


    public static String New_Group = "http://notes-keeper.000webhostapp.com/createGroup.php";
    public static String Delete_url = "http://notes-keeper.000webhostapp.com/deleteNoteApp.php";



    public MyDialogs(Context context,String tag){
        c = context;
        TAG = tag;
    }

    public MyDialogs(Context context,String tag,int index){
        c = context;
        TAG = tag;
        delete_id = index;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (TAG.equals(TAG_NEW_GROUP_MAIN)) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_new_group, null, false);

            mRequest = Volley.newRequestQueue(c);

            progressDialog = new ProgressDialog(c);

            final AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setView(v);
            Button button = (Button) v.findViewById(R.id.group_title_save);
            editText = (EditText) v.findViewById(R.id.group_title);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String f = editText.getText().toString();
                    if (f.equals("")) {
                        Toast.makeText(c, "enter a title", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.setTitle("Please wait...");
                        progressDialog.show();
                        new cr_gr().execute();
                    }
                    //dismiss();
                }
            });
            return builder.create();
        }else if (TAG.equals(TAG_DELETE_NOTE_EDIT)){
            View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_delete, null, false);

            mRequest = Volley.newRequestQueue(c);

            progressDialog = new ProgressDialog(c);

            final AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setView(v);
            Button yes = (Button) v.findViewById(R.id.delete_yes);
            Button no = (Button) v.findViewById(R.id.delete_no);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.setTitle("Please wait...");
                    progressDialog.show();
                    new delete().execute();
                    //dismiss();
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            return builder.create();
        }else {
            return null;
        }

    }




    public class cr_gr extends AsyncTask {

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



    StringRequest r = new StringRequest(Request.Method.POST, New_Group, new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            //j.setText(s);
            try {
                JSONObject object = new JSONObject(s);
                boolean error = object.getBoolean("error");
                if (!error){
                    Toast.makeText(c,"Group created successfully",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    dismiss();
                    //done  = true;
                }else{
                    //done = false;
                    //new sa().execute();
                    //err.setText(object.getString("message"));
                    progressDialog.dismiss();
                    Toast.makeText(c, "Dsd " + object.getString("message")
                            ,Toast.LENGTH_LONG).show();
                }
            }catch (JSONException e){
                //done = false;
                Toast.makeText(c,"json exception : " +
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
            Toast.makeText(c,"Connection error \n   please try again ",
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }) {

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> parameters  = new HashMap<String, String>();
            ManageUser m = new ManageUser(c);
            parameters.put("id_admin",String.valueOf(m.GetUserId()));
            parameters.put("groupname",editText.getText().toString());
            return parameters;
        }
    };

    StringRequest del = new StringRequest(Request.Method.POST, Delete_url, new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            //Toast.makeText(c,s,Toast.LENGTH_LONG).show();
            //j.setText(s);
            try {
                JSONObject object = new JSONObject(s);
                boolean error = object.getBoolean("error");
                if (!error){
                    Toast.makeText(c,"Note deleted",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    dismiss();
                    //done  = true;
                }else{
                    //done = false;
                    //new sa().execute();
                    //err.setText(object.getString("message"));
                    progressDialog.dismiss();
                    Toast.makeText(c,object.getString("message")
                            ,Toast.LENGTH_LONG).show();
                }
            }catch (JSONException e){
                //done = false;
                Toast.makeText(c,"json exception : " +
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
            Toast.makeText(c,"Connection error \n   please try again ",
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }) {

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> parameters  = new HashMap<String, String>();
            parameters.put("id",String.valueOf(delete_id));
            return parameters;
        }
    };


    public class delete extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            mRequest.add(del);

            return null;
        }

    }


}
