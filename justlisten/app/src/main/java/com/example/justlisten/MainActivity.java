package com.example.justlisten;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.mobisec.intent.action.FLAG_ANNOUNCEMENT");
    }
}