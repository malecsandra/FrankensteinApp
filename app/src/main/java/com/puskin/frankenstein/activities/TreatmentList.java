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
import com.puskin.frankenstein.adapters.LabTestAdapter;
import com.puskin.frankenstein.adapters.TreatmentAdapter;
import com.puskin.frankenstein.events.TestListEvent;
import com.puskin.frankenstein.events.TreatmentListEvent;
import com.puskin.frankenstein.models.AppointmentTreatment;
import com.puskin.frankenstein.models.User;
import com.puskin.frankenstein.network.NetworkHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class TreatmentList extends AppCompatActivity {

    Realm realm;
    TreatmentAdapter treatmentAdapter;
    ArrayList<AppointmentTreatment> appointmentTreatments;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_treatments)
    RecyclerView rvTreatments;
    @Bind(R.id.pbTreatmentList)
    ProgressBar pbTreatmentList;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_list);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();
        User loggedUser = realm.where(User.class).findFirst();

        setupUI();

        NetworkHelper.getTreatmentList(loggedUser.getPerson().getPersonId());
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
    public void treatmentEventCatch(TreatmentListEvent event){
        if(event.getResponseCode() == 200){
            appointmentTreatments = new ArrayList<>();
            appointmentTreatments.addAll(event.getTreatments());

            treatmentAdapter = new TreatmentAdapter(this, appointmentTreatments);
            rvTreatments.setAdapter(treatmentAdapter);

            pbTreatmentList.setVisibility(View.GONE);
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
        getSupportActionBar().setTitle("Treatments");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvTreatments.setLayoutManager(llm);

    }
}
