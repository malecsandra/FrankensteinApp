package com.puskin.frankenstein.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.puskin.frankenstein.R;
import com.puskin.frankenstein.adapters.DoctorAdapter;
import com.puskin.frankenstein.adapters.LabTestAdapter;
import com.puskin.frankenstein.events.TestListEvent;
import com.puskin.frankenstein.models.AppointmentTestSet;
import com.puskin.frankenstein.models.Doctor;
import com.puskin.frankenstein.models.User;
import com.puskin.frankenstein.network.NetworkHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

public class LaboratoryTests extends AppCompatActivity {
    Realm realm;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_labTests)
    RecyclerView rvLabTests;
    @Bind(R.id.pbTestList)
    ProgressBar pbTestList;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    LabTestAdapter laAdapter;
    ArrayList<AppointmentTestSet> testSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory_tests);
        ButterKnife.bind(this);

        testSets = new ArrayList<>();

        realm = Realm.getDefaultInstance();
        User loggedUser = realm.where(User.class).findFirst();

        setupUI();

        NetworkHelper.getLabTests(loggedUser.getPerson().getPersonId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void testListEventCatch(TestListEvent event){
        if(event.getResponseCode() == 200){
            testSets.addAll(event.getTestSets());

            laAdapter = new LabTestAdapter(this, testSets);
            rvLabTests.setAdapter(laAdapter);

            pbTestList.setVisibility(View.GONE);
        }
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
        getSupportActionBar().setTitle("Laboratory Tests");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvLabTests.setLayoutManager(llm);

    }
}
