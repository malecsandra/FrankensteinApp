package com.puskin.frankenstein.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.puskin.frankenstein.AlarmReceiver;
import com.puskin.frankenstein.R;
import com.puskin.frankenstein.models.AlarmModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class AlarmActivity extends AppCompatActivity {
    Realm realm;
    @Bind(R.id.textView_drugName)
    TextView textViewDrugName;
    @Bind(R.id.button_OK)
    Button buttonOK;

    String alarmID;
    AlarmModel ringingAlarm;
    @Bind(R.id.textView_pillDose)
    TextView textViewPillDose;

    Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        alarmID = getIntent().getStringExtra("alarmID");

        realm = Realm.getDefaultInstance();

        ringingAlarm = realm.where(AlarmModel.class).equalTo(AlarmModel.FIELD_ALARMID, alarmID).findFirst();

        textViewDrugName.setText(ringingAlarm.getDrugName());
        textViewPillDose.setText(Integer.toString(ringingAlarm.getPillsPerDose()));

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleNextAlarm();
            }
        });

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(this, alarmUri);
        ringtone.play();

    }

    private void scheduleNextAlarm() {
        Log.d("DBG", "Total Doses: " + ringingAlarm.getDoses());
        if (ringingAlarm.getCurrentDose() + 1 <= ringingAlarm.getDoses()) {
            Log.d("DBG", "Can increment Dose. Incrementing to " + (ringingAlarm.getCurrentDose() + 1));
            realm.beginTransaction();
            ringingAlarm.setCurrentDose(ringingAlarm.getCurrentDose() + 1);
            realm.commitTransaction();

            Intent alarmIntent = new Intent(this, AlarmReceiver.class);
            alarmIntent.putExtra("alarmID", ringingAlarm.getAlarmID());
            PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(this, AlarmReceiver.ALARM_REQUEST, alarmIntent, 0);

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(ringingAlarm.calculateNextAlarm());

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingAlarmIntent);

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy' ; 'HH:mm");

            Toast.makeText(this, "Next dose: " + sdf.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
            ringtone.stop();
            finish();
        } else {
            Toast.makeText(this, "This was the last dose", Toast.LENGTH_SHORT).show();
            ringtone.stop();
            finish();
        }
    }
}
