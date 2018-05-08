package cz.cvut.sindepe8.feeder.helpers;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by petrs on 08-May-18.
 */

public class DownloadWakeLockHelper {

    private static final String LOCK_NAME = "cz.cvut.sindepe8";
    private static PowerManager.WakeLock sWakeLock = null;

    public static synchronized void acquire(Context context) {
        if (sWakeLock == null) {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            sWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOCK_NAME);
        }
        sWakeLock.acquire();
    }

    public static synchronized void release() {
        if (sWakeLock != null) {
            sWakeLock.release();
        }
    }

}
