package com.example.gnirts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String aux = foo();
        Log.i("MOBISEC", "The result of foo is: " +aux);
        String[] ps = "Patata-frita".split(foo());
        for(String s: ps) {
            Log.i("MOBISEC", "The result of foo is: " + s);
        }
        char[] syms = new char[4];
        String bar = "-";
        for (int i = 0; i < syms.length; i++) {
            syms[i] = bar.charAt(0);
            Log.i("MOBISEC", "The text is: " + syms[i]);
        }
        int sum = 0;
        for (char c : syms) {
            sum += c;
        }
        Log.i("MOBISEC", "The value of _ is: " + sum);


    }

    public static String foo() {
        String s = "Vm0wd2QyVkZNVWRYV0docFVtMVNWVmx0ZEhkVlZscDBUVlpPVmsxWGVIbFdiVFZyVm0xS1IyTkliRmRXTTFKTVZsVmFWMVpWTVVWaGVqQTk=";
        for (int i = 0; i < 10; i++) {
            s = new String(Base64.decode(s, 0));
        }
        return s;
    }
}