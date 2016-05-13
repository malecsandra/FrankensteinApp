package com.puskin.frankenstein;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.puskin.frankenstein.activities.AlarmActivity;

/**
 * Created by rakatan on 13.05.2016.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {
    public static final int ALARM_REQUEST = 19;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DBG", "Something should be happening now!");
//
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alarmUri == null) {
//            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        }
//        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
//        ringtone.play();

        // Starts a service that will do whatever the heck we want
//        ComponentName comp = new ComponentName(context.getPackageName(),
//                AlarmActivity.class.getName());

        PowerManager.WakeLock wakeLock;

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "Potato");
        wakeLock.acquire();

        Intent i = new Intent();
        i.setClassName(context.getPackageName(), AlarmActivity.class.getName());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

//        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
