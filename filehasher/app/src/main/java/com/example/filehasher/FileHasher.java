package com.example.filehasher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;


public class FileHasher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri fileURI = getIntent().getData();
        Log.i("MOBISEC", fileURI.toString());
        String hash = null;
        try {
            hash = calcHash(fileURI);
        } catch (IOException e) {
            Log.i("MOBISEC", e.toString());
        }
        Log.i("MOBISEC", hash);
        // return the hash in a "result" intent
        Intent resultIntent = new Intent();
        resultIntent.putExtra("hash", hash);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private String calcHash(Uri fileURI) throws IOException {
        String path = fileURI.toString().replace("file://","");
        Log.i("MOBISEC", path);
        File dat = new File(path);
        FileInputStream fis = null;
        byte[] data = new byte[(int) dat.length()];
        try {
            fis = new FileInputStream(path);
            //read file into bytes[]
            fis.read(data);

        } catch (IOException e) {
            Log.i("MOBISEC", e.toString());
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        MessageDigest digest=null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            Log.i("MOBISEC", e1.toString());
        }
        return String.format("%0" + (digest.digest(data).length*2) + "X", new BigInteger(1, digest.digest(data))).toLowerCase();
    }
}
