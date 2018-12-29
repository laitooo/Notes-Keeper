package zxc.laitooo.noteskeeper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class SignupActivity extends AppCompatActivity {

    EditText email,username,password1,password2;
    Button j;
    TextView err;
    String em,un,pa1,pa2;
    ProgressDialog progressDialog;

    public static String Signup_URL = "http://notes-keeper.000webhostapp.com/home.php";

    private RequestQueue mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText)findViewById(R.id.signup_email);
        username = (EditText)findViewById(R.id.signup_username);
        password1 = (EditText)findViewById(R.id.signup_passowrd1);
        password2 = (EditText)findViewById(R.id.signup_passowrd2);
        err = (TextView) findViewById(R.id.pass_error);

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setCancelable(true);

        mRequest = Volley.newRequestQueue(getApplicationContext());

        j  = (Button)findViewById(R.id.button_signup);
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                err.setText("");
                em = email.getText().toString();
                un = username.getText().toString();
                pa1 = password1.getText().toString();
                pa2 = password2.getText().toString();
                if(!em.equals("")){
                    if (!un.equals("")) {
                        if (!pa1.equals("")){
                            if (!pa2.equals("")){
                                if (pa1.length()>7){
                                    if (pa1.equals(pa2)){
                                        progressDialog.setTitle("Please wait...");
                                        progressDialog.show();
                                        new si().execute();
                                    }else {
                                        err.setText("Passowrd doesn't match");
                                    }
                                }else {
                                    err.setText("password at least 8 charracters");
                                }
                            }else {
                                err.setText("Please confirm your password");
                            }
                        }else {
                            err.setText("Please enter your password");
                        }
                    }else {
                        err.setText("Please enter your username");
                    }
                }else {
                    err.setText("Please enter your email");
                }
            }
        });
    }

    class si extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            StringRequest r = new StringRequest(Request.Method.POST, Signup_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    //j.setText(s);
                    try {
                        JSONObject object = new JSONObject(s);
                        boolean error = object.getBoolean("error");
                        if (!error){
                            ManageUser m = new ManageUser(getApplicationContext());
                            m.SaveUser(object.getInt("id"),object.getString("username"),
                                    object.getString("email"));
                            progressDialog.dismiss();
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            progressDialog.dismiss();
                            err.setText(object.getString("message"));
                            //Toast.makeText(getApplicationContext(),
                            //        object.getString("message"),Toast.LENGTH_LONG).show();
                        }
                    }catch (JSONException e){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"json exception : " +
                                e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Connection error\n   please try again ",Toast.LENGTH_LONG).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> parameters  = new HashMap<String, String>();
                    parameters.put("var","signup");
                    parameters.put("username",un);
                    parameters.put("email",em);
                    parameters.put("password",pa1);
                    return parameters;
                }
            };
            mRequest.add(r);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }



    }

    public void logAct(View view){
        startActivity(new Intent(getApplicationContext(),SignupActivity.class));
        finish();
    }
}
