package com.puskin.frankenstein.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.puskin.frankenstein.R;
import com.puskin.frankenstein.adapters.DatesSpinnerAdapter;
import com.puskin.frankenstein.events.AppointmentSubmitedEvent;
import com.puskin.frankenstein.events.AvailableDatesEvent;
import com.puskin.frankenstein.models.AppointmentSubmitModel;
import com.puskin.frankenstein.models.Doctor;
import com.puskin.frankenstein.models.ScheduleModel;
import com.puskin.frankenstein.models.User;
import com.puskin.frankenstein.network.NetworkHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class AppointmentCreator extends AppCompatActivity {

    Doctor doctor;
    @Bind(R.id.textView_labelDoctorName)
    TextView textViewLabelDoctorName;
    @Bind(R.id.textView_labelSpeciality)
    TextView textViewLabelSpeciality;
    @Bind(R.id.button_selectDate)
    Button buttonSelectDate;
    @Bind(R.id.spinner_availableHours)
    Spinner spinnerAvailableHours;
    @Bind(R.id.button_submitAppointment)
    Button buttonSubmitAppointment;

    Date selectedDate;
    Calendar nowCalendar;
    @Bind(R.id.linearLayout_timePickLayout)
    LinearLayout linearLayoutTimePickLayout;
    @Bind(R.id.progressBar_availableHours)
    ProgressBar progressBarAvailableHours;
    @Bind(R.id.label_selectedDate)
    TextView labelSelectedDate;
    @Bind(R.id.textView_selectedDate)
    TextView textViewSelectedDate;

    User user;
    Realm realm;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_creator);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();

        doctor = (Doctor) getIntent().getParcelableExtra("doctor");

        textViewLabelDoctorName.setText(doctor.getName() + doctor.getSurname());
        textViewLabelSpeciality.setText(doctor.getSpeciality().getSpecialityName());

        nowCalendar = Calendar.getInstance();

        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentCreator.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.d("DBG", "Potato");
                        linearLayoutTimePickLayout.setVisibility(View.INVISIBLE);
                        progressBarAvailableHours.setVisibility(View.VISIBLE);
                        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                        selectedDate = calendar.getTime();

                        textViewSelectedDate.setText(new SimpleDateFormat("dd.MMM.yyyy").format(calendar.getTime()));
                        labelSelectedDate.setVisibility(View.VISIBLE);
                        textViewSelectedDate.setVisibility(View.VISIBLE);
                        buttonSubmitAppointment.setVisibility(View.INVISIBLE);

                        NetworkHelper.checkAvailableTimes(new ScheduleModel(doctor.getDoctorId(), calendar.getTime()));
                    }
                }, nowCalendar.get(Calendar.YEAR), nowCalendar.get(Calendar.MONTH), nowCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });

        buttonSubmitAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentSubmitModel appointmentSubmitModel = new AppointmentSubmitModel(-1,
                        user.getPerson().getPersonId(),
                        doctor.getDoctorId(),
                        (Date) spinnerAvailableHours.getSelectedItem(),
                        0);

                buttonSubmitAppointment.setVisibility(View.INVISIBLE);
                linearLayoutTimePickLayout.setVisibility(View.INVISIBLE);
                progressBarAvailableHours.setVisibility(View.VISIBLE);

                NetworkHelper.createAppointmment(appointmentSubmitModel);
            }
        });

        setupUI();
    }

    void setupUI() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Appointment");
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
    public void onDatesAvailableReturned(AvailableDatesEvent event) {
        if (event.getResponseCode() == 200) {
            if (event.getAvailableDates().size() > 0) {
                DatesSpinnerAdapter datesSpinnerAdapter = new DatesSpinnerAdapter(this, event.getAvailableDates());
                spinnerAvailableHours.setAdapter(datesSpinnerAdapter);

                progressBarAvailableHours.setVisibility(View.GONE);
                linearLayoutTimePickLayout.setVisibility(View.VISIBLE);

                buttonSubmitAppointment.setVisibility(View.VISIBLE);
                Log.d("DBG", "Available Dates: " + event.getAvailableDates().size());
            }
            else
            {
                progressBarAvailableHours.setVisibility(View.GONE);
                Toast.makeText(this, "No available hours. Choose another date.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Subscribe
    public void onAppointmentSubmited(AppointmentSubmitedEvent event) {
        if (event.getResponseCode() == 200 || event.getResponseCode() == 204) {
            Toast.makeText(this, "Appointment created successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Unable to create appointment", Toast.LENGTH_SHORT).show();
            buttonSubmitAppointment.setVisibility(View.VISIBLE);
            linearLayoutTimePickLayout.setVisibility(View.VISIBLE);
            progressBarAvailableHours.setVisibility(View.INVISIBLE);
        }
    }

}
