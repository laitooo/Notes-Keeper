package zxc.laitooo.noteskeeper;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    CollapsingToolbarLayout coll;
    Button upload;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = (TextView)findViewById(R.id.profile_email);
        coll = (CollapsingToolbarLayout)findViewById(R.id.profile_collapse);
        upload = (Button)findViewById(R.id.profile_upload);

        ManageUser m = new ManageUser(getApplicationContext());
        email.setText(m.GetUserEmail());
        coll.setTitle(m.GetUserName());
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Photo uploaded",Toast.LENGTH_LONG).show();
            }
        });
    }
}
