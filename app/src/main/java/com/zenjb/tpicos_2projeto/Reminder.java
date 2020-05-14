package com.zenjb.tpicos_2projeto;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Random;

public class Reminder extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(context,"Time to get up",Toast.LENGTH_SHORT).show();

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {1000};
        vibrator.vibrate(pattern, 0);
        Log.d("TOPICOS", "Ring Ring Notificação Reminder 1!");



        String title = "Sabia que?";

        ArrayList<String> message_list = new ArrayList<String>();
        message_list.add("Falta menos de uma hora para dormir, se quiser ter um bom desempenho amanhã e dormir melhor deverá alterar o seu ecrã para o modo noite se existir, ou pelo menos deverá baixar o brilho do seu ecrã");
        message_list.add("Sabia que o uso do telefone uma hora antes de dormir provoca insonias? O recomendável é evitar usar o telefone uma hora antes de dormir.");
        message_list.add("Está cientificamente comprovado que trabalhar na cama faz com que adormecer seja mais dificil pois confunde o cerebro sobre qual é o lugar para dormir.");
        //String message = "Deve reduzir o brilho do seu ecrã";
        Random r = new Random();
        int lowRange = 0;
        int highRange = message_list.size()-1;
        int numero_da_string_escolhida = r.nextInt(highRange -lowRange ) + lowRange;
        String message =  message_list.get(numero_da_string_escolhida);


        Intent snoozeIntent = new Intent(context, DesligarAlarme.class);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "lemubitA")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(largeIcon)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message)
                        .setBigContentTitle(title)
                        .setSummaryText("1 hora para dormir"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

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
    }
}