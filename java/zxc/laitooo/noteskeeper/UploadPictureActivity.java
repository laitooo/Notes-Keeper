package zxc.laitooo.noteskeeper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadPictureActivity extends AppCompatActivity {

    Button upload;
    Button select;
    ImageView view;

    boolean isSelected;

    public Bitmap bitmap;

    ProgressDialog progressDialog;

    public static String upload_url = "http://notes-keeper.000webhostapp.com/uploadPicture.php";

    private RequestQueue mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);

        progressDialog = new ProgressDialog(UploadPictureActivity.this);
        progressDialog.setCancelable(true);

        isSelected = false;

        bitmap = null;

        upload = (Button)findViewById(R.id.button_upload_image);
        select = (Button)findViewById(R.id.button_select_image);
        view = (ImageView) findViewById(R.id.imageview_select);

        mRequest = Volley.newRequestQueue(getApplicationContext());

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new upload().execute();
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,534);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==534 && resultCode==RESULT_OK){
            try {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    view.setImageBitmap(bitmap);
                    isSelected = true;
                }catch (IOException r){
                    Toast.makeText(getApplicationContext(),"Dsd2 " + r.getMessage(),Toast.LENGTH_LONG).show();
                }
                /*Uri uri = data.getData();
                String[] pr = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri,pr,null,null,null);
                cursor.moveToFirst();
                int coli = cursor.getColumnIndex(pr[0]);
                String path = cursor.getString(coli);
                Toast.makeText(getApplicationContext(),"path : " + path,Toast.LENGTH_SHORT).show();
                cursor.close();*/


            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Dsd " + e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }


    class upload extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            StringRequest r = new StringRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    //j.setText(s);
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    try {
                        JSONObject object = new JSONObject(s);
                        boolean error = object.getBoolean("error");
                        if (!error){
                            progressDialog.dismiss();
                            //Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            //startActivity(i);
                            //finish();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext()," message : " +
                                    object.getString("message"),Toast.LENGTH_LONG).show();
                            //err.setText(object.getString("message"));
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
                    parameters.put("picture",getStringImage(bitmap));
                    parameters.put("extension","png");
                    parameters.put("id_user",String.valueOf(new ManageUser(getApplicationContext()).GetUserId()));
                    return parameters;
                }
            };
            r.setShouldCache(false);
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
}
