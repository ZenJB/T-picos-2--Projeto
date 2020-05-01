package com.zenjb.tpicos_2projeto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
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
