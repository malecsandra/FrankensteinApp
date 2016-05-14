package com.puskin.frankenstein.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.puskin.frankenstein.AlarmReceiver;
import com.puskin.frankenstein.R;
import com.puskin.frankenstein.models.AlarmModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class AddAlarm extends AppCompatActivity {

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
    @Bind(R.id.textView_alarmDate)
    TextView textViewAlarmDate;
    @Bind(R.id.textView_setAlarm)
    TextView textViewSetAlarm;
    @Bind(R.id.editText_pillsPerDose)
    TextInputEditText editTextPillsPerDose;

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
                saveAndSetAlarm();
            }
        });

        textViewSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        String[] spinnerArray = getResources().getStringArray(R.array.periodicitySizes);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodType.setAdapter(spinnerArrayAdapter);

        spinnerPeriodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DBG", "Spinner Item Selected");
                selectedMeasure = parsePeriodicityMeasure((String) parent.getItemAtPosition(position));
                Log.d("DBG", "Selected: " + selectedMeasure);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    int parsePeriodicityMeasure(String periodicity) {
        switch (periodicity) {
            case "Minute(s)":
                Log.d("DBG", "Selecting MINUTE " + Calendar.MINUTE);
                return Calendar.MINUTE;
            case "Hour(s)":
                Log.d("DBG", "Selecting HOUR_OF_DAY " + Calendar.HOUR_OF_DAY);
                return Calendar.HOUR_OF_DAY;
            case "Day(s)":
                Log.d("DBG", "Selecting DAY_OF_MONTH " + Calendar.DAY_OF_MONTH);
                return Calendar.DAY_OF_MONTH;
            case "Week(s)":
                Log.d("DBG", "Selecting WEEK_OF_MONTH " + Calendar.WEEK_OF_MONTH);
                return Calendar.WEEK_OF_MONTH;
            case "Month(s)":
                Log.d("DBG", "Selecting MONTH " + Calendar.MONTH);
                return Calendar.MONTH;
            default:
                Log.d("DBG", "Selecting DEFAULT " + -1);
                return -1;
        }
    }

    void showDatePicker() {
        final Calendar nowCalendar = new GregorianCalendar();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddAlarm.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("DBG", "Potato");
                Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                selectedDate = calendar.getTime();
                showTimePicker();
            }

        }, nowCalendar.get(Calendar.YEAR), nowCalendar.get(Calendar.MONTH), nowCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    void showTimePicker() {
        final Calendar nowCalendar = new GregorianCalendar();

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddAlarm.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(selectedDate);
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                selectedDate = calendar.getTime();

                setDateView();
            }
        }, nowCalendar.get(Calendar.HOUR_OF_DAY), nowCalendar.get(Calendar.MINUTE), true);

        timePickerDialog.show();
    }

    void setDateView() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy' ; 'HH:mm");
        textViewAlarmDate.setText(sdf.format(selectedDate));
        textViewSetAlarm.setText("Change");
        textViewAlarmDate.setVisibility(View.VISIBLE);
    }

    boolean validateFields() {
        return editTextDrugName.getText().toString().length() > 0 &&
                textViewAlarmDate.getText().toString().length() > 0 &&
                editTextTotalDoses.getText().toString().length() > 0 &&
                editTextPeriodValue.getText().toString().length() > 0 &&
                editTextPillsPerDose.getText().toString().length() > 0;
    }

    void saveAndSetAlarm() {
        if (validateFields()) {
            // Save alarm
            AlarmModel alarmModel = new AlarmModel();
            alarmModel.setAlarmID(UUID.randomUUID().toString());
            alarmModel.setDrugName(editTextDrugName.getText().toString());
            alarmModel.setStartDate(selectedDate);
            alarmModel.setDoses(Integer.parseInt(editTextTotalDoses.getText().toString()));
            alarmModel.setPillsPerDose(Integer.parseInt(editTextPillsPerDose.getText().toString()));
            alarmModel.setCurrentDose(1);
            alarmModel.setPeriodicity((Integer.parseInt(editTextPeriodValue.getText().toString())));
            alarmModel.setPeriodicityMeasure(selectedMeasure);
            alarmModel.setShowDetails(false);

            realm.beginTransaction();
            realm.copyToRealm(alarmModel);
            realm.commitTransaction();

            // Set Alarm;
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            // This is the intentto create the pending intent with in order to delete it too
            Intent alarmIntent = new Intent(this, AlarmReceiver.class);
            alarmIntent.putExtra("alarmID", alarmModel.getAlarmID());

            PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(this, AlarmReceiver.ALARM_REQUEST, alarmIntent, 0);
            //
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(alarmModel.calculateNextAlarm());

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingAlarmIntent);

            Log.d("DBG", "Alarm is set");

            finish();
        } else {
            Toast.makeText(AddAlarm.this, "Complete all of the fields please", Toast.LENGTH_SHORT).show();
        }
    }
}
