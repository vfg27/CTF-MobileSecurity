package com.example.filebrowser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/* loaded from: classes2.dex */
public class QueryActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_operator);
        String out = null;
        try {
            Intent intent = getIntent();
            String oper = intent.getStringExtra("oper");
            String arg = intent.getStringExtra("arg");
            MainActivity.logQuery(this, oper, arg);
            Process p = null;
            if (oper.equals("ls")) {
                String[] cmd = {"sh", "-c", "ls " + arg};
                p = Runtime.getRuntime().exec(cmd);
            } else if (oper.equals("du")) {
                String[] cmd2 = {"sh", "-c", "du " + arg};
                p = Runtime.getRuntime().exec(cmd2);
            } else if (oper.equals("cat")) {
                out = "cat has been momentarily disabled for security reasons";
            } else {
                out = "oper '" + oper + "' not supported";
            }
            if (out == null) {
                if (p != null) {
                    BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    String line = stdOut.readLine();
                    if (line == null) {
                        line = stdErr.readLine();
                    }
                    if (line != null) {
                        out = line;
                    } else {
                        out = "error while reading output";
                    }
                } else {
                    out = "error while exec command";
                }
            }
        } catch (Exception e) {
            out = "exception while exec command";
            Log.e("MOBISEC", "Exception while executing operation:" + Log.getStackTraceString(e));
        }
        if (getIntent().getBooleanExtra("debug", false)) {
            MainActivity.writeToFile("/sdcard/browser.log", out);
        }
        reply(out);
    }

    public void reply(String msg) {
        Intent i = new Intent();
        i.putExtra("result", msg);
        setResult(-1, i);
        finish();
    }
}