package com.puskin.frankenstein.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.puskin.frankenstein.OutRequests;
import com.puskin.frankenstein.R;
import com.puskin.frankenstein.models.User;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

public class Home extends AppCompatActivity {

    public static final String BASE_URL = "http://192.168.100.7/FrankensteinWS/api/";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navView_drawer)
    NavigationView navViewDrawer;
    @Bind(R.id.linearLayout_doctors)
    LinearLayout linearLayoutDoctors;
    @Bind(R.id.linearLayout_reminder)
    LinearLayout linearLayoutReminder;
    @Bind(R.id.linearLayout_locations)
    LinearLayout linearLayoutLocations;
    @Bind(R.id.linearLayout_contact)
    LinearLayout linearLayoutContact;
    View headerView;
    private ActionBarDrawerToggle drawerToggle;
    CircleImageView profileImage;
    TextView userNameText;
    TextView userEmailText;

    Realm realm;

    private static final int TELEPHONE_REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();

        headerView = navViewDrawer.getHeaderView(0);
        profileImage = (CircleImageView) headerView.findViewById(R.id.profile_image);
        userNameText = (TextView) headerView.findViewById(R.id.textView_userName);
        userEmailText = (TextView) headerView.findViewById(R.id.textView_userEmail);


        setupUI();

        setupButtons();
    }

    void setupUI() {
        setSupportActionBar(toolbar);

        setupDrawer();
        populateDrawer();
    }

    void setupButtons() {
        linearLayoutDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, DoctorList.class);
                startActivity(i);
            }
        });

        linearLayoutReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, PillReminder.class);
                startActivity(i);
            }
        });

        linearLayoutLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, ClinicActivity.class);
                startActivity(i);
            }
        });

        linearLayoutContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutRequests.dialPhone(Home.this, "0724042756");
            }
        });
    }

    void setupDrawer() {
        // Initializing Drawer Layout and ActionBarToggle
        android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


        navViewDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    case R.id.analize:
//                        Toast.makeText(getApplicationContext(), "Analize Selected", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Home.this, LaboratoryTests.class);
                        startActivity(i);
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
    }

    private void populateDrawer() {
        User loggedInUser = realm.where(User.class).findFirst();
        userNameText.setText(loggedInUser.getFullName());
        userEmailText.setText(loggedInUser.getPerson().getEmail());
    }
}
