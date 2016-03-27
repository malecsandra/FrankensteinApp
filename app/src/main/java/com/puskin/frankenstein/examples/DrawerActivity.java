package com.puskin.frankenstein.examples;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.puskin.frankenstein.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DrawerActivity extends AppCompatActivity {
// TODO Add comments to everything

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.navView_drawer)
    NavigationView navViewDrawer;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        navViewDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){

                    case R.id.analize:
                        Toast.makeText(getApplicationContext(), "Analize Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.istoric:
                        Toast.makeText(getApplicationContext(),"Istoric Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.programari:
                        Toast.makeText(getApplicationContext(),"Programari Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.itemX:
                        Toast.makeText(getApplicationContext(),"Item X Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.itemY:
                        Toast.makeText(getApplicationContext(),"Item Y Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.itemZ:
                        Toast.makeText(getApplicationContext(),"Item Z Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.itemT:
                        Toast.makeText(getApplicationContext(),"Item T Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

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
    }
}
