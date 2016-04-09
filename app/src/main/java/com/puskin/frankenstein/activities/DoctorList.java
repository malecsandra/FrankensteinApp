package com.puskin.frankenstein.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.puskin.frankenstein.R;
import com.puskin.frankenstein.adapters.DoctorAdapter;
import com.puskin.frankenstein.events.DoctorEvent;
import com.puskin.frankenstein.models.Doctor;
import com.puskin.frankenstein.network.NetworkHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class DoctorList extends AppCompatActivity {

    DoctorAdapter doctorAdapter;
    @Bind(R.id.rw_doctors)
    RecyclerView rwDoctors;
    @Bind(R.id.pbDoctor)
    ProgressBar pbDoctor;
    private RealmList<Doctor> doctors;
    @Bind(R.id.button_DoctorsTest)
    Button buttonDoctorsTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        ButterKnife.bind(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rwDoctors.setLayoutManager(llm);

        doctorAdapter = new DoctorAdapter(new RealmList<Doctor>());
        rwDoctors.setAdapter(doctorAdapter);

        NetworkHelper.getDoctors();
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
    public void onDoctorEvent(DoctorEvent doctorEvent) {
        Log.d("DBG", "onDoctorEvent: abcd");
        if (doctorEvent.getResponseCode() == 200) {
            pbDoctor.setVisibility(View.GONE);
            doctors = doctorEvent.getDoctors();
            doctorAdapter.setDoctorList(doctors);
        }
    }
}
