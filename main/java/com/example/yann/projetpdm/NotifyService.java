package com.example.yann.projetpdm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.yann.projetpdm.Helper.TimeHelper;

public class NotifyService extends Service {

    public static final String REFRESH_TIME_INTENT = "REFRESH_TIME_INTENT";
    public static final String KEY_EXTRA_LONG_TIME_IN_MS = "timeInMs";
    private static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private CountDownTimer mCountDownTimer;
    private PendingIntent contentIntent;
    private boolean isRunning = false;

    public NotifyService() {
        super();
    }

    @Override
    public void onCreate() {
        contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isRunning) {
            long timeInMs = intent.getLongExtra(KEY_EXTRA_LONG_TIME_IN_MS, 0L);
            startTimer(timeInMs);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void startTimer(long initTimer) {
        mCountDownTimer = new CountDownTimer(initTimer, 1000) {


            @Override
            public void onTick(long millisUntilFinished) {
                isRunning = true;
                Log.i(NotifyService.class.getName(), "Time " + millisUntilFinished / 1000);
                showNotificationTime(millisUntilFinished);
                sendEvent(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                isRunning = false;
                Log.i(NotifyService.class.getName(), "onFinish");
                sendEvent(0L);
                showNotification();
                NotifyService.this.stopSelf();
            }
        };

        mCountDownTimer.start();
    }

    private void sendEvent(long millisUntilFinished) {

        Intent intent = new Intent(REFRESH_TIME_INTENT);
        intent.putExtra(KEY_EXTRA_LONG_TIME_IN_MS, millisUntilFinished);
        sendBroadcast(intent);
    }

    private void showNotification() {
        NotificationCompat.Builder notifBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.alert_tiny_ic)
                        .setContentTitle("Terminé")
                        .setContentIntent(contentIntent)
                        .setContentText("Temps écoulé !!!");
        mNotificationManager.notify(NOTIFICATION_ID, notifBuilder.build());
    }

    private void showNotificationTime(long timeInMs) {
        Integer[] minSec = TimeHelper.getMinSec(timeInMs);
        NotificationCompat.Builder notifBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.alert_tiny_ic)
                        .setContentTitle("Compteur")
                        .setContentIntent(contentIntent)
                        .setContentText(getString(R.string.timeleft, minSec[TimeHelper.MINUTE], minSec[TimeHelper.SECOND]));
        mNotificationManager.notify(NOTIFICATION_ID, notifBuilder.build());
    }
}