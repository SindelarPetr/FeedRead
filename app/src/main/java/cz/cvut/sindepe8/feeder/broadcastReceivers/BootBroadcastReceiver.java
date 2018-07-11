package cz.cvut.sindepe8.feeder.broadcastReceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.Console;
import java.util.logging.Logger;

import cz.cvut.sindepe8.feeder.activities.MainActivity;

public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Start alarms for downloading
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        long interval = 2 * AlarmManager.INTERVAL_HOUR;
        long time = System.currentTimeMillis() + interval;
        Intent launchIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, launchIntent, PendingIntent.FLAG_NO_CREATE);

        // pendingIntent is null when the alarm is already set
        if(pendingIntent != null) {
            am.setRepeating(AlarmManager.RTC_WAKEUP, time, interval, pendingIntent);
            Log.i("BootBroadcastReceiver", "Alarm has been set.");

        }
    }
}
