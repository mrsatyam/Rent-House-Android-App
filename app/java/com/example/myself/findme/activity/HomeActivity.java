package com.example.myself.findme.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.myself.findme.R;
import com.example.myself.findme.fragments.HomeFragment;
import com.example.myself.findme.fragments.PlaceholderFragment;
import com.example.myself.findme.util.PrefUtils;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;


    // NAVIGATION MENU
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Homes For All");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = PrefUtils.getValue(HomeActivity.this, "app_pref", "userid");

                if (userid != null) {
                    Intent intent = new Intent(HomeActivity.this, PostActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(HomeActivity.this, "Login to proceed", Toast.LENGTH_SHORT).show();
                }

            }
        });


        PrefUtils.setValue(this, "app_pref", "city", "selected");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);

        String userid = PrefUtils.getValue(this, "app_pref", "userid");


        TextView login = (TextView) view.findViewById(R.id.Login);
        TextView signup = (TextView) view.findViewById(R.id.Signup);
        if (userid != null) {
            login.setVisibility(View.INVISIBLE);
            signup.setVisibility(View.INVISIBLE);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


        //   if (navigationView != null) {
        //      setupDrawerContent(navigationView);
        //  }


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

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


            new MaterialDialog.Builder(this)
                    .title("Select City")
                    .customView(R.layout.custom_change_location, true)
                    .positiveText("Submit")
                    .negativeText("Exit")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                           Spinner etfeedback =(Spinner) dialog.findViewById(R.id.SCity);

                            String city = etfeedback.getSelectedItem().toString();

                          PrefUtils.setValue(HomeActivity.this, "app_pref", "cityname",city);

                            Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    })
                    .show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_helpline) {

            Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+918445630369"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return true;
            }
            startActivity(intent2);

        }
        else if (id == R.id.nav_project) {

            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("table", "project");
            startActivity(intent);

        } else if (id == R.id.nav_hostel) {

            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("table", "hostelpg");
            startActivity(intent);

        } else if (id == R.id.nav_house) {
            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("table", "house");
            startActivity(intent);

        } else if (id == R.id.nav_rent) {
            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("table", "rentlease");
            startActivity(intent);

        } else if (id == R.id.Logout) {

            PrefUtils.setValue(this, "app_pref", "userid", null);
            PrefUtils.setValue(this, "app_pref", "emailid", null);
            PrefUtils.setValue(this, "app_pref", "islogin", null);
            Intent intent2 = new Intent(this,SplashActivity.class);
            startActivity(intent2);
            finish();

        }
        else if(id==R.id.nav_feedback)
        {
           new MaterialDialog.Builder(this)
                    .title("Send us Feedback")
                    .customView(R.layout.feedback_view, true)
                    .positiveText("Submit")
                    .negativeText("Exit")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            EditText etfeedback =(EditText) dialog.findViewById(R.id.input_feedback);

                            if(etfeedback.getText().toString()!=null||etfeedback.getText().toString()!="") {
                                Intent emailIntent = new Intent(
                                        android.content.Intent.ACTION_SEND);
                                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                                        new String[]{"admin@travelworld.com"});
                                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                                        "Feedback");
                                emailIntent.setType("plain/text");
                                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                                        etfeedback.getText().toString());
                                startActivity(emailIntent);
                            }
                            else
                            {
                                etfeedback.setError("Feedback can't be empty.");
                            }

                        }
                    })
                    .show();
        }
        else if(id==R.id.nav_post) {
            String userid = PrefUtils.getValue(HomeActivity.this, "app_pref", "userid");

            if (userid != null) {
                if (userid != null) {
                    Intent intent = new Intent(HomeActivity.this,PostActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(HomeActivity.this,"Login to proceed",Toast.LENGTH_SHORT).show();
                }

            }
        }
        else if(id==R.id.nav_inbox) {
            String userid = PrefUtils.getValue(HomeActivity.this, "app_pref", "userid");


                if (userid != null) {
                    Intent intent = new Intent(HomeActivity.this,InboxActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(HomeActivity.this,"Login to proceed",Toast.LENGTH_SHORT).show();
                }


        }
        else if(id==R.id.nav_outbox) {
            String userid = PrefUtils.getValue(HomeActivity.this, "app_pref", "userid");


                if (userid != null) {
                    Intent intent = new Intent(HomeActivity.this,SentActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(HomeActivity.this,"Login to proceed",Toast.LENGTH_SHORT).show();
                }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            switch (position) {
                case 0:
                    return HomeFragment.newInstance(position + 1);
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "HOME";
                case 1:
                    return "OFFER";

            }
            return null;
        }
    }


   /* private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }
*/

}
