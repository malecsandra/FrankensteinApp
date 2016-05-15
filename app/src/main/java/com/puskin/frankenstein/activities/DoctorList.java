package com.puskin.frankenstein.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.puskin.frankenstein.R;
import com.puskin.frankenstein.adapters.DoctorAdapter;
import com.puskin.frankenstein.events.DoctorClickEvent;
import com.puskin.frankenstein.events.DoctorEvent;
import com.puskin.frankenstein.models.Doctor;
import com.puskin.frankenstein.network.NetworkHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class DoctorList extends AppCompatActivity {

    DoctorAdapter doctorAdapter;
    @Bind(R.id.rw_doctors)
    RecyclerView rwDoctors;
    @Bind(R.id.pbDoctor)
    ProgressBar pbDoctor;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.editText_filterName)
    TextInputEditText editTextFilterName;
    @Bind(R.id.button_cancelNameFilter)
    Button buttonCancelNameFilter;
    private RealmList<Doctor> doctors;

    private RealmList<Doctor> filteredDoctorsName;
    private RealmList<Doctor> filteredDoctorsSpeciality;

    private RealmList<Doctor> finalFilteredDoctors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        ButterKnife.bind(this);

        setupUI();

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
            filteredDoctorsName = new RealmList<>();
            filteredDoctorsName.addAll(doctors);

            filteredDoctorsSpeciality = new RealmList<>();
            filteredDoctorsSpeciality.addAll(doctors);

            finalFilteredDoctors = new RealmList<>();
            finalFilteredDoctors.addAll(intersect(filteredDoctorsName, filteredDoctorsSpeciality));

            doctorAdapter.setDoctorList(finalFilteredDoctors);
        }
    }

    @Subscribe
    public void onDoctorClickEvent(DoctorClickEvent clickEvent) {
        Intent i = new Intent(this, AppointmentCreator.class);
        i.putExtra("doctor", clickEvent.getDoctor());

        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_doctors_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.item_filterSpeciality:
                showFilterDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void setupUI() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Doctor List");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rwDoctors.setLayoutManager(llm);

        doctorAdapter = new DoctorAdapter(new RealmList<Doctor>());
        rwDoctors.setAdapter(doctorAdapter);

        editTextFilterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                RealmList<Doctor> foundDoctors = new RealmList<>();

                for (Doctor doctor : doctors) {
                    String doctorName = doctor.getName() + " " + doctor.getSurname();
                    doctorName = doctorName.toLowerCase();
                    if (doctorName.contains(s.toString().toLowerCase())) {
                        foundDoctors.add(doctor);
                    }
                }

                filteredDoctorsName.clear();
                filteredDoctorsName.addAll(foundDoctors);

                finalFilteredDoctors.clear();
                finalFilteredDoctors.addAll(intersect(filteredDoctorsName, filteredDoctorsSpeciality));

                doctorAdapter.notifyDataSetChanged();
            }
        });

        buttonCancelNameFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextFilterName.setText("");
                filteredDoctorsName.clear();
                filteredDoctorsName.addAll(doctors);

                doctorAdapter.notifyDataSetChanged();
            }
        });
    }

    void showFilterDialog(){
        final CharSequence[] specialities = getResources().getStringArray(R.array.medicalSpeciality);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a speciality");
        builder.setItems(specialities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RealmList<Doctor> foundDoctors = new RealmList<>();

                if (which == 0) {
                    filteredDoctorsSpeciality.clear();
                    filteredDoctorsSpeciality.addAll(doctors);

                    finalFilteredDoctors.clear();
                    finalFilteredDoctors.addAll(intersect(filteredDoctorsName, filteredDoctorsSpeciality));

                    doctorAdapter.notifyDataSetChanged();

                    return;
                }

                for (Doctor doctor : doctors) {
                    String doctorSpeciality = doctor.getSpeciality().getSpecialityName();
                    doctorSpeciality = doctorSpeciality.toLowerCase();
                    if (doctorSpeciality.contains(specialities[which].toString().toLowerCase())) {
                        foundDoctors.add(doctor);
                    }
                }

                filteredDoctorsSpeciality.clear();
                filteredDoctorsSpeciality.addAll(foundDoctors);

                finalFilteredDoctors.clear();
                finalFilteredDoctors.addAll(intersect(filteredDoctorsName, filteredDoctorsSpeciality));


                doctorAdapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }

    RealmList<Doctor> intersect(RealmList<Doctor> list1, RealmList<Doctor> list2){
        RealmList<Doctor> intersectionResult = new RealmList<>();
        intersectionResult.addAll(list1);
        intersectionResult.retainAll(list2);

        return intersectionResult;
    }
}
