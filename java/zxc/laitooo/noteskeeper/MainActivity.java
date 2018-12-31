package zxc.laitooo.noteskeeper;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /***********************************************************************************************
     **TODO:
     *  1- Show only users notes
     *  2- what happens when there is no internet
     *  3- offline features
     *  4- check if there is internet connection
     **********************************************************************************************/

    String UserName;
    private int ID;

    public static TextView empty;

    public static ArrayList<Note> list_notes,list_groups;
    private RequestQueue mRequest;
    public static NotesAdapter adapter,adapter2;
    static ProgressDialog progressDialog;

    public static String Print_url = "http://notes-keeper.000webhostapp.com/printNotes.php";


    ImageView profile;
    TextView profileName;

    public static SwipeRefreshLayout swipe;


    public static Context c;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private ViewPager mViewPager;


    static nl j;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_main);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_main);
        tabLayout.setupWithViewPager(mViewPager);

        j = new nl();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ManageUser m = new ManageUser(getApplicationContext());
        UserName = m.GetUserName();
        ID = m.GetUserId();
        /*s = " id = " + m.GetUserId()+ "\n user name = " + m.GetUserName() +
                "\n email = " + m.GetUserName();
        TextView k = (TextView)findViewById(R.id.username);
        k.setText(s);*/
        mRequest = Volley.newRequestQueue(getApplicationContext());

        progressDialog = new ProgressDialog(MainActivity.this);
        c = MainActivity.this;

        list_notes = new ArrayList<>();
        list_groups = new ArrayList<>();
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
        new nl().execute();

        View header = navigationView.getHeaderView(0);
        profile = (ImageView)header.findViewById(R.id.imageView_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });
        profileName = (TextView)header.findViewById(R.id.username_profile);
        profileName.setText(UserName);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            ManageUser m = new ManageUser(getApplicationContext());
            m.DeleteUser();
            Toast.makeText(getApplicationContext(), "you logged out Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_donate) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class nl extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {

            StringRequest request = new StringRequest(Request.Method.POST, Print_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try{
                        JSONObject no = new JSONObject(s);
                        JSONArray notes = no.getJSONArray("notes");
                        //Toast.makeText(c,s,Toast.LENGTH_LONG).show();
                        if (notes.length()==0){
                            progressDialog.dismiss();
                            empty.setText("You dont have notes yet");
                        }else {

                            for (int i = notes.length() - 1; i >= 0; i--) {
                                JSONObject note = notes.getJSONObject(i);
                                list_notes.add(new Note(note.getInt("id"),note.getString("title"),
                                        note.getString("content"), note.getString("date")));
                            }
                            progressDialog.dismiss();
                            adapter.notifyDataSetChanged();
                        }
                        if (swipe.isRefreshing()) {
                            swipe.setRefreshing(false);
                        }
                        j = new nl();


                    }catch (JSONException e){
                        progressDialog.dismiss();
                        swipe.setRefreshing(false);

                        //empty.setText("Json Exception \n      try again");
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        j = new nl();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    swipe.setRefreshing(false);

                    //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                    empty.setText("Connection Error \n      try again");
                    j = new nl();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> parameters  = new HashMap<String, String>();
                    parameters.put("id_user",String.valueOf(ID));
                    return parameters;
                }
            };

            mRequest.add(request);
            return null;
        }

    }

    /*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh){
            progressDialog.show();
            empty.setText("");
            list_notes.clear();
            adapter.notifyDataSetChanged();
            new nl().execute();
        }

        return super.onOptionsItemSelected(item);
    }


    */




    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /*

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

            int index = getArguments().getInt(ARG_SECTION_NUMBER);
            if (index == 0) {
                inflater.inflate(R.menu.refresh, menu);
                setHasOptionsMenu(true);
            }else if(index == 1){
                inflater.inflate(R.menu.groups,menu);
                setHasOptionsMenu(true);
            }
            super.onCreateOptionsMenu(menu,inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.refresh:
                    Toast.makeText(getContext(),"refresh",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.cr_group:
                    Toast.makeText(getContext(),"create",Toast.LENGTH_SHORT).show();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }*/

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int index = getArguments().getInt(ARG_SECTION_NUMBER);
            if (index == 0) {

                View rootView = inflater.inflate(R.layout.content_main, container, false);

                empty = (TextView) rootView.findViewById(R.id.no_notes);

                //setHasOptionsMenu(true);

                swipe = (SwipeRefreshLayout)rootView.findViewById(R.id.refresh_layot);
                //swipe.setColorScheme(Color.BLUE,Color.RED,Color.GREEN);
                swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipe.setRefreshing(true);
                        ds();
                    }
                });


                FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab_main);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(c,NewNoteActivity.class));
                    }
                });


                //empty.setText("You dont have notes yet");


                //list.add(new Note("Note1 ","I have to do new project in android","12/21/2018"));
                //list.add(new Note("Note2 ","I have to do new project in android","1/5/1999"));
                //list.add(new Note("Note3 ","I have to do new project in android","2015/2/2"));
                //list.add(new Note("Book Recommondation System ","Try to contact with randosh babiker about book " +
                //        "recommendation system app","12/17/2018"));
                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_notes);
                recyclerView.setHasFixedSize(true);
                //list_notes.add(new Note("a","s","2"));
                //list_notes.add(new Note("a","s","2"));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(c);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new NotesAdapter(list_notes, c);
                recyclerView.setAdapter(adapter);
                return rootView;
            }else if(index == 1 ){
                View rootView = inflater.inflate(R.layout.groups_layout, container, false);

                //empty = (TextView) rootView.findViewById(R.id.no_notes);


                //empty.setText("You dont have notes yet");
                FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab_groups);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NewGroupDialog dialogss = new NewGroupDialog(c);
                        dialogss.show(getFragmentManager(),"");
                    }
                });

                //list.add(new Note("Note1 ","I have to do new project in android","12/21/2018"));
                //list.add(new Note("Note2 ","I have to do new project in android","1/5/1999"));
                //list.add(new Note("Note3 ","I have to do new project in android","2015/2/2"));
                //list.add(new Note("Book Recommondation System ","Try to contact with randosh babiker about book " +
                //        "recommendation system app","12/17/2018"));
                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_groups);
                recyclerView.setHasFixedSize(true);
                list_groups.add(new Note(2464,"dsd","addasdasd","2019/12/23"));
                list_groups.add(new Note(7655,"dsdsd asdas dasdas d","addasd dsadasd","2019/12/23"));
                list_groups.add(new Note(8855,"dsds dad dasd","addasd 23sda 34asd","2019/12/23"));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(c);
                recyclerView.setLayoutManager(layoutManager);
                adapter2 = new NotesAdapter(list_groups, c);
                recyclerView.setAdapter(adapter2);
                return rootView;
            }
            return null;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "My Notes";
                case 1:
                    return "Groups";
            }
            return null;
        }
    }

    @Override
    protected void onRestart() {
        progressDialog.show();
        empty.setText("");
        list_notes.clear();
        adapter.notifyDataSetChanged();
        j.execute();
        super.onResume();
    }

    public static void ds(){
        //progressDialog.show();
        empty.setText("");
        list_notes.clear();
        adapter.notifyDataSetChanged();
        j.execute();
    }
}

