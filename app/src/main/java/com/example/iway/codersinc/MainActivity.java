package com.example.iway.codersinc;

        import android.app.ActionBar;
        import android.app.FragmentTransaction;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.res.Configuration;
        import android.support.design.widget.CoordinatorLayout;
        import android.support.design.widget.NavigationView;
        import android.support.design.widget.TabLayout;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.support.v4.view.ViewPager;
        import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;


        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.example.iway.codersinc.Contests.CollegeContests;
        import com.example.iway.codersinc.Contests.ConnectivityReceiver;
        import com.example.iway.codersinc.Contests.Contact;
        import com.example.iway.codersinc.Contests.CurrentContests;
        import com.example.iway.codersinc.Contests.MyApplication;
        import com.example.iway.codersinc.Contests.UpcommingContests;
        import com.example.iway.codersinc.Contests.config;
        import com.example.iway.codersinc.Contests.DatabaseHandler;
        import com.example.iway.codersinc.Drawer.graph.GraphActivity;
        import com.example.iway.codersinc.LoginSignup.LoginActivity;
        import com.roughike.bottombar.BottomBar;
        import com.roughike.bottombar.OnMenuTabSelectedListener;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener,ConnectivityReceiver.ConnectivityReceiverListener {

    // Declaring Your View and Variables
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    CharSequence Titles[] = {"Current", "Upcoming", "College"};
    int Numboftabs = 3;
    //listview through jsonparsing
    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    ProgressDialog pdialog;
    private ProgressDialog loading;
    private static ArrayList<DataModel> data;
    public static DatabaseHandler db;
    private String name, begin_time, end_time, type, begin_date, end_date, link, site;
    private String NAME;
    // URL to get contacts JSON
    private static String url = "http://meghna.net16.net/codechef_json_first.php";
    ArrayList<HashMap<String, String>> contactList;
    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);
        data = new ArrayList<>();

        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.username);


        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Intent intent = getIntent();
        Bundle intentBundle = intent.getExtras();
        NAME = intentBundle.getString("username");
        Log.e("main",NAME+" ");
        nav_user.setText(NAME);
        //coordinatorLayout = (CoordinatorLayout) findViewById(R.id.three_buttons_activity);
       /* BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottom_bar, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.sort:
                        break;
                    case R.id.filter:
                        break;
                }
            }
        });*/
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });


    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

//set up view pager all functions

    private void setupViewPager(ViewPager viewPager) {

        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CurrentContests(), "Current");
        adapter.addFrag(new UpcommingContests(), "Upcoming");
        adapter.addFrag(new CollegeContests(), "College");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    //drawer toolbar
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }


    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logout();
            return true;
        }
        if(id==R.id.filter){
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    public void logout(){
                SharedPreferences preferences =getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(MainActivity.this, "You Have Successfully Logged out", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                 startActivity(intent);

    }

    public void getData() {

        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);
        String url = config.DATA_URL.trim();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void showJSON(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(config.JSON_ARRAY);
            for (int i = 0; i < result.length(); i++) {
                JSONObject collegeData = result.getJSONObject(i);
                name = collegeData.getString(config.KEY_NAME);
                type = collegeData.getString(config.KEY_TYPE);
                site = collegeData.getString(config.KEY_SITE);
                begin_date = collegeData.getString(config.KEY_BEGINDATE);
                begin_time = collegeData.getString(config.KEY_BEGINTIMING);
                end_date = collegeData.getString(config.KEY_ENDDATE);
                end_time = collegeData.getString(config.KEY_ENDTIMING);
                link = collegeData.getString(config.KEY_LINK);
                db.addContact(new Contact(name, type, site, begin_date, begin_time, end_date, end_time, link));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    public void showSnack(boolean isConnected) {
        //String message;
        //int color;
        if (isConnected) {
            getData();
        } else {

            /*// Reading all contacts
            Log.d("Reading: ", "Reading all contacts..");
            List<Contact> contacts = db.getAllContacts();
            Log.e("ddddd", "hello");
            int i = 1;
            for (Contact cn : contacts) {
                String log = "name: " + cn.getName() + "  ,type: " + cn.getSite() + " ,begindate: " + cn.getBegindate() + " ,begin: " + cn.getBegin() + "  ,enddate: " + cn.getEnddate() + " ,end: " + cn.getEnd() + ",link: " + cn.getLink();
                Log.d("Name: ", log);
               *//* DataModel dm = new DataModel(cn.getSite(), cn.getName(), cn.getBegindate(), cn.getBegin(), cn.getEnddate(), cn.getEnd());
                data.add(dm);*/


            //}

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
        checkConnection();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        Log.e("qqq", "connected");
        showSnack(isConnected);

    }

  /*  @Override
    public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.navprofile) {
                return true;
                // Handle the camera action
            } else if (id == R.id.navmanage) {
                return true;

            } else if (id == R.id.navgraph) {
                Log.e("main", "navgraph");

                graph();
                return true;


            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        //super.onOptionsItemSelected(item);
            return onOptionsItemSelected(item);
        }*/
  //select the drawer
  public void selectDrawerItem(MenuItem menuItem) {
      // Create a new fragment and specify the fragment to show based on nav item clicked
      // Fragment fragment = null;
      // Class fragmentClass;
       switch (menuItem.getItemId()) {
            case R.id.navprofile:
               /* Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                MainActivity.this.startActivity(intent);*/
                break;
            case R.id.navmanage:
                /*Intent intent1 = new Intent(MainActivity.this, ManageHandlesActivity.class);
                MainActivity.this.startActivity(intent1);*/
                break;
            case R.id.navgraph:
                Intent intent2 = new Intent(MainActivity.this, GraphActivity.class);
                MainActivity.this.startActivity(intent2);
                break;
        }

        try {
            Intent intent = new Intent(MainActivity.this, GraphActivity.class);
            //MainActivity.this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

      // Insert the fragment by replacing any existing fragment
      //  FragmentManager fragmentManager = getSupportFragmentManager();
      //fragmentManager.beginTransaction().replace(R.id.tab_pager, fragment).commit();

      // Highlight the selected item has been done by NavigationView
      menuItem.setChecked(true);
      // Set action bar title
      setTitle(menuItem.getTitle());
      // Close the navigation drawer
      mDrawer.closeDrawers();
  }

    public void graph(){
        Intent ig=new Intent(MainActivity.this, GraphActivity.class);
        startActivity(ig);
    }

}






