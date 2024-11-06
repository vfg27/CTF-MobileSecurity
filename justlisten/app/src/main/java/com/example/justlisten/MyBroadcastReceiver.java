package com.example.justlisten;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MOBISEC";
    @Override
    public void onReceive(Context context, Intent intent) {
        String log = intent.getStringExtra("flag");
        Log.d(TAG, log);

    }
}