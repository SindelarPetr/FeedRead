package cz.cvut.sindepe8.feeder.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import cz.cvut.sindepe8.feeder.helpers.DownloadWakeLockHelper;
import cz.cvut.sindepe8.feeder.services.DownloadService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Start service for downloading files.
        DownloadWakeLockHelper.acquire(context);
        context.startService(new Intent(context, DownloadService.class));

    }
}
