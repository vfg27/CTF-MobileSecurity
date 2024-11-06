package com.example.justask;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<Intent> aActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Bundle bund = data.getExtras();
                        Set<String> keys = bund.keySet();
                        Iterator<String> keysIt = keys.iterator();
                        while (keysIt.hasNext()) {
                            Log.i("MOBISEC", bund.getString((keysIt.next())));
                        }
                    }
                });

        ActivityResultLauncher<Intent> bActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Bundle bund = data.getExtras();
                        Set<String> keys = bund.keySet();
                        Iterator<String> keysIt = keys.iterator();
                        while (keysIt.hasNext()) {
                            Log.i("MOBISEC", bund.getString((keysIt.next())));
                        }
                    }
                });

        ActivityResultLauncher<Intent> cActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Bundle bund = data.getExtras();
                        Set<String> keys = bund.keySet();
                        Iterator<String> keysIt = keys.iterator();
                        while (keysIt.hasNext()) {
                            Log.i("MOBISEC", bund.getString((keysIt.next())));
                        }
                    }
                });

        ActivityResultLauncher<Intent> dActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Bundle bund = data.getExtras();
                        Set<String> keys = bund.keySet();
                        Iterator<String> keysIt = keys.iterator();
                        while (keysIt.hasNext()) {
                            String key = keysIt.next();
                            Object obj = bund.get(key);
                            if (obj.getClass().equals(String.class)) {
                                Log.i("MOBISEC", bund.getString(key));
                            }else if (obj.getClass().equals(Bundle.class)){
                                recBundle(bund.getBundle(key));
                            }
                        }
                    }
                });

        Intent intentA = new Intent();
        intentA.setClassName("com.mobisec.justask", "com.mobisec.justask.PartOne");
        aActivityResultLauncher.launch(intentA);

        Intent intentB = new Intent("com.mobisec.intent.action.JUSTASK");
        intentB.setClassName("com.mobisec.justask", "com.mobisec.justask.PartTwo");
        bActivityResultLauncher.launch(intentB);

        Intent intentC = new Intent();
        intentC.setClassName("com.mobisec.justask", "com.mobisec.justask.PartThree");
        cActivityResultLauncher.launch(intentC);

        Intent intentD = new Intent("com.mobisec.intent.action.JUSTASKBUTNOTSOSIMPLE");
        intentD.setClassName("com.mobisec.justask", "com.mobisec.justask.PartFour");
        dActivityResultLauncher.launch(intentD);
    }
    private void recBundle(Bundle bund) {
        Set<String> keys = bund.keySet();
        Iterator<String> keysIt = keys.iterator();
        while (keysIt.hasNext()) {
            String k = keysIt.next();
            Object obj = bund.get(k);
            if (obj.getClass().equals(String.class)) {
                Log.i("MOBISEC", bund.getString(k));
            }else if (obj.getClass().equals(Bundle.class)){
                recBundle(bund.getBundle(k));
            }
        }
    }
}