package com.example.serialintent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.PathClassLoader;


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
                        Log.i("MOBISEC", "ENTER");
                        Intent data = result.getData();
                        Bundle bund = data.getExtras();
                        Log.i("MOBISEC", "MAYBE HERE?");
                        Class<?> flagC = null;
                        try {
                            PathClassLoader classLoader=new PathClassLoader(
                                    getPackageManager().getApplicationInfo("com.mobisec.serialintent",0).sourceDir,
                                    ClassLoader.getSystemClassLoader());
                            flagC  = classLoader.loadClass("com.mobisec.serialintent.FlagContainer");
                            bund.setClassLoader(classLoader);
                        }catch (Exception e){
                            Log.i("MOBISEC", e.toString());
                        }
                        Log.i("MOBISEC", "OR HERE?");
                        try {
                            Log.i("MOBISEC", "NOT NOTHING");
                            //Method method1 = flagC.getDeclaredMethod("getParts");
                            //Method method2 = flagC.getDeclaredMethod("getPerms");
                            Method method3 = flagC.getDeclaredMethod("getFlag");
                            Log.i("MOBISEC", "FIRST ROW");
                            //method1.setAccessible(true);
                            //method2.setAccessible(true);
                            method3.setAccessible(true);
                            Log.i("MOBISEC", "SECOND ROW");
                            //method1.invoke(bund.get("flag"));
                            //method2.invoke(bund.get("flag"));
                            String res = (String) method3.invoke(bund.get("flag"));
                            Log.i("MOBISEC", res);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            Log.i("MOBISEC", e.toString());
                        }
                        /*
                        try {
                            Method method1 = fc.getClass().getDeclaredMethod("getParts");
                            Method method2 = fc.getClass().getDeclaredMethod("getPerms");
                            Method method3 = fc.getClass().getDeclaredMethod("getFlag");
                            method1.invoke(fc);
                            method2.invoke(fc);
                            String res = (String) method3.invoke(fc);
                            Log.i("MOBISEC", res);
                        } catch (NoSuchMethodException e) {
                            Log.i("MOBISEC", e.toString());
                        } catch (InvocationTargetException e) {
                            Log.i("MOBISEC", e.toString());
                        } catch (IllegalAccessException e) {
                            Log.i("MOBISEC", e.toString());
                        }
                        */
                    }
                });

        Intent intentA = new Intent();
        intentA.setClassName("com.mobisec.serialintent", "com.mobisec.serialintent.SerialActivity");
        aActivityResultLauncher.launch(intentA);
    }
}