package com.example.a52951.labtarea05;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Switch switchA;
    CameraManager camera;
    Vibrator vibrator;
    String cameraId, message;
    Notification.Builder notiBuilder;
    NotificationManager notiManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchA = findViewById(R.id.switch1);
        final long pattern[] = {0,200,100,300,400};

        context = getApplicationContext();

        int NOTIFY_ID = 1234;
        Notification.Builder NotifBuilder = new Notification.Builder(this);
        NotifBuilder.setSmallIcon(R.mipmap.ic_launcher);
        NotifBuilder.setContentTitle("Important Notification");
        NotifBuilder.setContentText("This is the detail of the notification");

        Intent notificationIntent = new Intent(context, ChildActivity.class);
        notificationIntent.putExtra("myData", "This string comes from the previous activity");
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotifBuilder.setContentIntent(contentIntent);

        NotificationManager MyNotification = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        MyNotification.notify(NOTIFY_ID, NotifBuilder.build());

        switchA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                try {
                    camera = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    cameraId = camera.getCameraIdList()[0];

                    message = (isCheck) ? "You turn on the light" :
                            "You turn off the light";

                    notiBuilder = getNotificationBuilder(message);

                    camera.setTorchMode(cameraId, isCheck);
                    notiManager.notify(createNotificationID(), notiBuilder.build());
                    vibrator.vibrate(pattern, -1);
                } catch (Exception e) { }
            }
        });
    }

    private Notification.Builder getNotificationBuilder(String message) {
        return new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Torch mode")
                .setContentText(message);
    }

    private int createNotificationID() {
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US)
                .format(new Date()));
    }
}