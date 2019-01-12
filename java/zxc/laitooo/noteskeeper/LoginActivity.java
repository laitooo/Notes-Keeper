package zxc.laitooo.noteskeeper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText email,passwod;
    Button j;
    TextView err;
    String em,pa;
    ProgressDialog progressDialog;

    //public static String Login_URL = "http://notes-keeper.000webhostapp.com/home.php";
    public static String Login_URL = "http://192.168.43.4:80/app/home.php";

    private RequestQueue mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ManageUser m = new ManageUser(getApplicationContext());
        if (m.isUserLogged()){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        email = (EditText)findViewById(R.id.login_email);
        passwod = (EditText)findViewById(R.id.login_password);
        err = (TextView) findViewById(R.id.pass_error);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(true);

        mRequest = Volley.newRequestQueue(getApplicationContext());

        j  = (Button)findViewById(R.id.button_login);
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isThereConnection()) {
                    err.setText("");
                    em = email.getText().toString();
                    pa = passwod.getText().toString();
                    if (!em.equals("")) {
                        if (!pa.equals("")) {
                            progressDialog.setTitle("Please wait...");
                            progressDialog.show();
                            new lo().execute();
                        } else {
                            err.setText("Please enter your password");
                        }
                    } else {
                        err.setText("Please enter your email");
                    }
                }else {
                    err.setText("No Internet Connection");
                }
            }
        });
    }

    class lo extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            StringRequest r = new StringRequest(Request.Method.POST, Login_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
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
                            //        ,Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(),"Connection error \n   please try again ",
                            Toast.LENGTH_LONG).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> parameters  = new HashMap<String, String>();
                    parameters.put("var","login");
                    parameters.put("email",em);
                    parameters.put("password",pa);
                    return parameters;
                }
            };

            r.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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

    public void signAct(View view){
        Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
        startActivity(intent);
        finish();
    }


    public boolean isThereConnection(){
        ConnectivityManager manager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo i = manager.getActiveNetworkInfo();
        //return i != null && i.isConnected();
        return true;
    }
}
