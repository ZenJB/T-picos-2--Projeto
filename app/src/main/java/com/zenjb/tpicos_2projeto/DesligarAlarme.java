package com.zenjb.tpicos_2projeto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

public class DesligarAlarme extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName receiver = new ComponentName(context, Alarme.class);
        PackageManager pm = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, Alarme.class);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Log.d("TOPICOS", "Alarme Desligado!");

    }
}
