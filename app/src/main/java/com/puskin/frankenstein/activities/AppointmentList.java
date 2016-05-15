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
import com.puskin.frankenstein.adapters.AppointmentAdapter;
import com.puskin.frankenstein.adapters.DoctorAdapter;
import com.puskin.frankenstein.adapters.LabTestAdapter;
import com.puskin.frankenstein.events.AppointmentEvent;
import com.puskin.frankenstein.events.AppointmentSubmitedEvent;
import com.puskin.frankenstein.events.TestListEvent;
import com.puskin.frankenstein.models.AppointmentModel;
import com.puskin.frankenstein.models.AppointmentSubmitModel;
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

public class AppointmentList extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_appointments)
    RecyclerView rvAppointments;
    @Bind(R.id.pbAppointment)
    ProgressBar pbAppointment;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    private ArrayList<AppointmentModel> appointemts;
    AppointmentAdapter appointmentAdapter;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        ButterKnife.bind(this);

        appointemts = new ArrayList<>();

        realm = Realm.getDefaultInstance();
        User loggedUser = realm.where(User.class).findFirst();

        setupUI();

        NetworkHelper.getAppointments(loggedUser.getPerson().getPersonId());
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
    public void appointmentListEventCatch(AppointmentEvent event){
        if(event.getResponseCode() == 200){
            appointemts.addAll(event.getAppointments());

            appointmentAdapter = new AppointmentAdapter(appointemts, this);
            rvAppointments.setAdapter(appointmentAdapter);

            pbAppointment.setVisibility(View.GONE);
        }
        else if(event.getResponseCode() == 204){
            appointmentAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void appointmentModifiedEvent(AppointmentSubmitedEvent event){
        if(event.getResponseCode() == 200 || event.getResponseCode() == 204){
            appointmentAdapter.notifyDataSetChanged();
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
        getSupportActionBar().setTitle("Appointment List");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvAppointments.setLayoutManager(llm);
    }

}
