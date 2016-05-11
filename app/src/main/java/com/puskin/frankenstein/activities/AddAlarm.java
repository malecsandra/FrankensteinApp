package com.puskin.frankenstein.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.puskin.frankenstein.R;
import com.puskin.frankenstein.adapters.DoctorAdapter;
import com.puskin.frankenstein.models.AlarmModel;
import com.puskin.frankenstein.models.Doctor;

import java.util.Date;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

public class AddAlarm extends AppCompatActivity {
    // TODO Launch dialog to select date on click on the text
    // TODO When date has been selected update the existing date and launch time dialog.
    // TODO Create interval measurement unit spinner values and corelation function. Use Calendar.year and such
    // TODO Maybe make some validations.

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.editText_drugName)
    TextInputEditText editTextDrugName;
    @Bind(R.id.editText_totalDoses)
    TextInputEditText editTextTotalDoses;
    @Bind(R.id.editText_periodValue)
    TextInputEditText editTextPeriodValue;
    @Bind(R.id.spinner_periodType)
    Spinner spinnerPeriodType;
    @Bind(R.id.button_saveAlarm)
    Button buttonSaveAlarm;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    Realm realm;
    Date selectedDate;
    int selectedMeasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();

        setupUI();
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
        getSupportActionBar().setTitle("Add Alarm");

        buttonSaveAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAlarm();
            }
        });
    }

    void saveAlarm() {
        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setAlarmID(UUID.randomUUID().toString());
        alarmModel.setDrugName(editTextDrugName.getText().toString());
        alarmModel.setStartDate(selectedDate);
        alarmModel.setDoses(Integer.parseInt(editTextTotalDoses.getText().toString()));
        alarmModel.setCurrentDose(1);
        alarmModel.setPeriodicity((Integer.parseInt(editTextPeriodValue.getText().toString())));
        alarmModel.setPeriodicity(selectedMeasure);
        alarmModel.setShowDetails(false);

        realm.beginTransaction();
        realm.copyToRealm(alarmModel);
        realm.commitTransaction();

        finish();
    }
}
