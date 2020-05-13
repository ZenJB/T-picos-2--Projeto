package com.zenjb.tpicos_2projeto;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private PendingIntent alarmIntent2;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/index.html");
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "Android");
        Log.d("TOPICOS", "App iniciada");

        //createAlarm("20","23");

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @JavascriptInterface
    public void createAlarm(String hora, String minuto){
        Log.d("TOPICOS", "Alarme Criado: "+hora+" "+minuto);

        /*
        Intent intent = new Intent(this,Alarme.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),23432,intent,0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(1000+4),pendingIntent);
        Toast.makeText(this,"Alarme set in 4 seconds",Toast.LENGTH_LONG).show();
*/

        /*
        Context context = this.getApplicationContext();
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarme.class);

        Intent intent2 = new Intent(context,Reminder.class);

        //alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmIntent = PendingIntent.getBroadcast(this,1,intent,0);

        alarmIntent2 = PendingIntent.getBroadcast(this,2,intent2,0);
        */

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //Obter a hora atual
        int hora_atual = (int)calendar.get(Calendar.HOUR_OF_DAY);
        int minuto_atual = (int)calendar.get(Calendar.MINUTE);

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minuto));
        calendar.set(Calendar.SECOND, 0);
        if(hora_atual > Integer.parseInt(hora)){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }else
            if(hora_atual == Integer.parseInt(hora) && minuto_atual > Integer.parseInt(minuto)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

        setBroadcastReceiverTimer(calendar, Alarme.class, 1);

        Calendar reminder = Calendar.getInstance();
        reminder.setTimeInMillis(calendar.getTimeInMillis());
        reminder.add(Calendar.HOUR_OF_DAY, -10);
        setBroadcastReceiverTimer(reminder, Reminder.class, 2);

        Calendar final_reminder = Calendar.getInstance();
        final_reminder.setTimeInMillis(calendar.getTimeInMillis());
        final_reminder.add(Calendar.MINUTE, -15);
        final_reminder.add(Calendar.HOUR_OF_DAY, -9);
        setBroadcastReceiverTimer(final_reminder, Reminder2.class, 3);

        /*
        Calendar reminder = Calendar.getInstance();

        reminder.setTimeInMillis(System.currentTimeMillis());

        if(Integer.parseInt(hora)<10)
        {
            reminder.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hora)+12+2);
        }
        else{
            reminder.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hora)-10);
        }
        reminder.set(Calendar.MINUTE,Integer.parseInt(minuto));
        reminder.set(Calendar.SECOND,0);
        */

        //alarmMgr.setExact(AlarmManager.RTC_WAKEUP,reminder.getTimeInMillis(),alarmIntent2);


        //alarmMgr.setExact(AlarmManager.RTC_WAKEUP,reminder.getTimeInMillis(),alarmIntent);

        //alarmMgr.setExact(AlarmManager.RTC_WAKEUP);
        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        //alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
          //      1000 * 60 * 20, alarmIntent);
        //alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),alarmIntent);

        saveSetting("activation", "true");
    }

    public void setBroadcastReceiverTimer(Calendar calendar, Class theClass, int id_do_broadcast){
        Context context = this.getApplicationContext();
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, theClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,id_do_broadcast,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelBroadcastReceiverTimer(Class theClass, int id_do_broadcast){
        Context context = this.getApplicationContext();
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, theClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,id_do_broadcast,intent,0);

        alarmManager.cancel(pendingIntent);
    }

    //Não está a funcionar
    @JavascriptInterface
    public void cancelAlarm(){
        try {
            cancelBroadcastReceiverTimer(Alarme.class,    1);
            cancelBroadcastReceiverTimer(Reminder.class,  2);
            cancelBroadcastReceiverTimer(Reminder2.class, 3);
            Log.d("TOPICOS", "Alarme cancelado");
            saveSetting("activation", "false");
        }catch(Exception err){
            Log.d("TOPICOS", err.toString());

        }
    }


    @JavascriptInterface
    public void showNotification(String title, String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "lemubitA")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(100, builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("lemubitA", title, importance);
            channel.setDescription(message);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showAlert(String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
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
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.topicos.despertador", Context.MODE_PRIVATE);
        return sharedPreferences.getString(setting, "null");
    }

    @JavascriptInterface
    public boolean saveSetting(String setting, String data){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.topicos.despertador", Context.MODE_PRIVATE);
        return sharedPreferences.edit().putString(setting, data).commit();
    }
}
