package com.puskin.frankenstein.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.puskin.frankenstein.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PillReminder extends AppCompatActivity {
    // TODO Get the alarms from the realm and setup the recyclerview
    // TODO Start work on the service that wakes up on every alarm madness.
    // TODO Check for a way to send notifications.

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView_alarms)
    RecyclerView recyclerViewAlarms;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.button_addAlarm)
    FloatingActionButton buttonAddAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_reminder);
        ButterKnife.bind(this);

        setupUI();

        buttonAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PillReminder.this, AddAlarm.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void setupUI() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pill Reminder");
    }
}
