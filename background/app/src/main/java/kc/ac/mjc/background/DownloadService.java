package kc.ac.mjc.background;

import static android.app.NotificationManager.IMPORTANCE_LOW;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class DownloadService extends Service {
    int count = 0; //0->100 까지 올라갈 변수
    Notification notification;      //사용자에게 보여질 알람

    final int NOTIFICATION_ID = 1000;
    NotificationCompat.Builder builder;
    NotificationManager nm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        nm = getSystemService(NotificationManager.class);

        String channelId = "download";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "download", IMPORTANCE_LOW);
            nm.createNotificationChannel(channel);
        }

        builder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("다운로드")
                .setContentText("다운로드중..")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setProgress(100, count, false)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        notification = builder.build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC);
        } else {
            startForeground(NOTIFICATION_ID, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 101; i += 5) {
                    count = i;
                    if (builder != null) {
                        builder.setProgress(100, count, false);
                        notification = builder.build();
                        nm.notify(NOTIFICATION_ID, notification);
                    }
                    Log.d("DownloadService", "count :" + count);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d("DownloadService", "interrupt");
                    }


                    if (count >= 100) {
                        nm.cancel(NOTIFICATION_ID);
                        stopSelf();
                    }
                    Intent intent = new Intent();
                    intent.setAction("download_progress");
                    intent.putExtra("progress", count);
                    intent.setPackage(getPackageName());
                    sendBroadcast(intent);  //download_progress 채널로 방송
                }
            }
        });
        t.start();

        return START_NOT_STICKY;
    }
}