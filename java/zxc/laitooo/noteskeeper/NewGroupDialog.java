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

public class NewGroupDialog extends DialogFragment {

    Context c;

    private RequestQueue mRequest;

    ProgressDialog progressDialog;

    EditText editText;


    public static String New_Group = "http://notes-keeper.000webhostapp.com/createGroup.php";

    public NewGroupDialog(Context context){
        c = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_new_group, null, false);

        mRequest = Volley.newRequestQueue(c);

        progressDialog = new ProgressDialog(c);

        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setView(v);
        Button button = (Button)v.findViewById(R.id.group_title_save);
        editText = (EditText)v.findViewById(R.id.group_title);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f = editText.getText().toString();
                if (f.equals("")) {
                    Toast.makeText(c,"enter a title",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Please wait...");
                    progressDialog.show();
                    new cr_gr().execute();
                }
                //dismiss();
            }
        });
        return builder.create();
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

}
