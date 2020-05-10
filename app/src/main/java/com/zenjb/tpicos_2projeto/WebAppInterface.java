package com.zenjb.tpicos_2projeto;


import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
        showNotification("Despertador", "Sabia que se deitar tarde faz mal ao seu cerebro?");
    }



    public void showNotification(String title, String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "lemubitA")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mContext);
        notificationManagerCompat.notify(100, builder.build());



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "definicao do alarme";
            String description = "o alarme foi definido";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("lemubitA", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }



    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showAlert(String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @JavascriptInterface
    public String getSetting(String setting){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                "com.topicos.despertador", Context.MODE_PRIVATE);
        return sharedPreferences.getString(setting, "null");
    }

    @JavascriptInterface
    public boolean saveSetting(String setting, String data){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                "com.topicos.despertador", Context.MODE_PRIVATE);
        return sharedPreferences.edit().putString(setting, data).commit();
    }
}
