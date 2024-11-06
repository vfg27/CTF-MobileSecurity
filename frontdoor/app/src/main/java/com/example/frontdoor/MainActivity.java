package com.example.frontdoor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String username = "testuser";
        String password = "passtestuser123";
        Log.i("MOBISEC","Getting flag....");
        try {
            String result = Flag.getFlag(username, password);
            Log.i("MOBISEC",result);
        } catch (Exception e) {
            Log.i("MOBISEC", "Exception while getting the flag:" + Log.getStackTraceString(e));
        }

    }
}