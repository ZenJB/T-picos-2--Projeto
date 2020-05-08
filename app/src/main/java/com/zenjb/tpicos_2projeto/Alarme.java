package com.zenjb.tpicos_2projeto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Alarme extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d("TOPICOS", "Ring Ring Alarme!");
        }
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