package com.evo.mitzoom.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.evo.mitzoom.ui.DipsOutboundCall;
import com.evo.mitzoom.ui.DipsSplashScreen;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("CEK","MyBroadcastReceiver");

        Intent alarmIntent = new Intent(context.getApplicationContext(), DipsOutboundCall.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.getApplicationContext().startActivity(alarmIntent);
    }
}
