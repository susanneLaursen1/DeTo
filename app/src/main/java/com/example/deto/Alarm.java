package com.example.deto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.view.View;

import java.util.Random;

public class Alarm extends AppCompatActivity {
//1. Notification channel
//2. Notification Builder
//3. Notification Manger
    private static final String Channel_Id = "Simplified_coding";
    private static final String Channel_Name = "Simplified Coding";
    private static final String Channel_Desc = "Simplified Coding Notification";
    final int random = new Random().nextInt(5) + 1;
    int number = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(Channel_Id,Channel_Name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(Channel_Desc);
            NotificationManager manger = getSystemService(NotificationManager.class);
            manger.createNotificationChannel(channel);
        }

        if(number >= 5){
            displayNotification();
        }

//        findViewById(R.id.buttonNotify).setOnClickListener(new View.OnClickListener() {
//            @Override
        //          public void onClick(View v) {
        //      displayNotification();
        //  }
        //});
    }
    public void displayNotification(){
        Intent fullScreenIntent = new Intent(this, SecondActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,Channel_Id)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("En borger har tegn på urinvejsinfektion")
                .setContentText("Se yderligere oplysninger i DeTo")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setFullScreenIntent(fullScreenPendingIntent, true);


        NotificationManagerCompat mNotificationMrg = NotificationManagerCompat.from(this);
        mNotificationMrg.notify(1,mBuilder.build());

    }


}