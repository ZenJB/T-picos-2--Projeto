package com.zenjb.tpicos_2projeto;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Alarme extends BroadcastReceiver {
    private MediaPlayer player;

    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(context,"Time to get up",Toast.LENGTH_SHORT).show();

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {1000, 500, 1000, 500, 1000};
        vibrator.vibrate(pattern, 0);
        Log.d("TOPICOS", "Ring Ring Alarme!");

        Uri alarmSound =
                RingtoneManager. getDefaultUri (RingtoneManager. TYPE_ALARM );
        MediaPlayer mp = MediaPlayer. create (context.getApplicationContext(), alarmSound);
        mp.start();


        String title = "Despertador";
        String message = "Está na hora de acordar";

        Intent snoozeIntent = new Intent(context, DesligarAlarme.class);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "lemubitA")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_launcher_background, "Desligar",
                        snoozePendingIntent);;

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(100, builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("lemubitA", title, importance);
            channel.setDescription(message);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        //NotificationHelper notificationHelper = new NotificationHelper(context);
        //NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        //notificationHelper.getManager().notify(1, nb.build());

        /*
        //On device boot
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d("TOPICOS", "Ring Ring Alarme!");
        }*/
    }
}

//if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
        //    Log.d("TOPICOS", "Ring Ring Alarme!");
        //}
        //Intent i = new Intent(context, MainActivity.class);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //context.startActivity(i);
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "lemubitA")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Alarme!!!")
                .setContentText("Acordaaaaa")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(100, builder.build());
    }
}
*/