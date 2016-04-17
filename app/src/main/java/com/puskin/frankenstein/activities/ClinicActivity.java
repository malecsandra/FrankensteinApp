package com.puskin.frankenstein.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.puskin.frankenstein.R;
import com.puskin.frankenstein.adapters.ClinicAdapter;
import com.puskin.frankenstein.adapters.DoctorAdapter;
import com.puskin.frankenstein.events.ClinicEvent;
import com.puskin.frankenstein.models.Clinic;
import com.puskin.frankenstein.models.Doctor;
import com.puskin.frankenstein.network.NetworkHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class ClinicActivity extends AppCompatActivity {

    ClinicAdapter clinicAdapter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rw_clinics)
    RecyclerView rwClinics;
    @Bind(R.id.pbClinics)
    ProgressBar pbClinics;

    private RealmList<Clinic> clinics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic);
        ButterKnife.bind(this);

        setupUI();

        NetworkHelper.getClinics();
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
    public void onClinicEvent(ClinicEvent clinicEvent) {
        Log.d("DBG", "onClinicEvent: clinic");
        if (clinicEvent.getResponseCode() == 200) {
            pbClinics.setVisibility(View.GONE);
            clinics = clinicEvent.getClinics();
            clinicAdapter.setClinicList(clinics);
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
        getSupportActionBar().setTitle("Clinic List");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rwClinics.setLayoutManager(llm);

        clinicAdapter = new ClinicAdapter(new RealmList<Clinic>(), this);
        rwClinics.setAdapter(clinicAdapter);
    }
}
