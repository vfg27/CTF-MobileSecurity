package com.example.nojumpstarts;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.security.Signature;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.io.BufferedReader;
import java.io.StringReader;



public class MainActivity extends AppCompatActivity {

    private static final String PRIVKEY = "-----BEGIN RSA PRIVATE KEY-----\nMIIEowIBAAKCAQEA0vKu6oHF6wkA+gDdqhcsreLzlY9KLdNQBSSrlvWcZxmMglQU\n4oL017CvuTAqSWJmPlsO0c0fNy6yE+iOA2SrhTM0RJ229fl6VDcFyHnG4grmcfsl\nj2J3lCAssdON5tgrbRvt/FRrkF06x7rLCWiQbcWZgt/Yj+ONbPHeHIImSzUkNkJC\nMMlwIv/l/Oz1gN6Pn6KZQxiASSqR5uS7v92vYXGpusLjvfLpFzCEBYbBEpguxmQF\npfvyKZQHiimiEgqvWvOaNV8FKNi2/eELD3jre6U0ng/eSlhP9FKrt109xdgw96ff\nVYK0o9o9IJm44RZFYvTjLIBGYI7uwaAgJK+rFQIDAQABAoIBACD/rbUpj9hwlCKH\nuCU/ctHQyuH+hFAe2kmzrtPyoADQ0lYg6RN2AO8syJBjpHnOVsgyXmMbf2KWf2z1\n2CFXwi0YEXkaYuCfoi9CXDk8M4xwwBPcgceoU6RsVBGNUolmD4UF9XifjCQNMYDF\nC7XxEYbafmUmty3rHtiIDYLQKUViRe/gisYtWC1OdAZ0s/Q/rdpf9EVCcmKDlDxc\n8zoRA8gmF4EwJrVwpqvdSqmwTQPGjrkFfv0fxJk5iYiId+FONKtcXFvmZ7i0o9bB\nBeJdmJySlOr/9oCs29R/ZUkW13H9zGftKX0pp64qgxy/yGoe/lQunzYete5l5X1t\ne/wnORECgYEA9ktAziObm/7uDHl3hpi6rj7SKE7EvxB2mvfLGfXNUM3t4c1K8Sdp\n61H7rufuv+Ebz4PpS7pm2sctUmXvlp0Z+HxOsqDEKU/0FByN2O8Qnc5H8yFfTVLR\nMYwS4EqvDcsG9J7YywlRlQ+z1Fy18VEgGcgWWoiKNAPrKcTMMoO7/dsCgYEA20LX\nPIxtS6J0s1Xk16kF9oCIKOiSsJ3vbRjSU2ffewOJYhCizMrjyDhfUcuED3mDGg0i\n7ctAWPuRfQ4z13ku5GCQ711mh5F3wA+mLr7vRVUwEk2F4PUL81LkwblJl4meb3Gj\n/pCiEeYy9mrr8N3JAxK0f4hYdITlmFtXvQtyZc8CgYEAjOnRUh+dUEsy94AnmqKX\nbEoVA2rNtmM8+Lz9PwUbSzgG+kHytrbOKwzk6tVYDABHYRsfx2wGnPWIQLSBt7J6\nwWidviWtFdXg2ADeR1fAMglrsPdPB3Zyqd5yjlEshr+6YwrfDotuWdJ6GO5SSpcX\nvqhz4ahtKgVz7pniGqS0UjECgYAMyymj7s2xzBjjHe/sZYBjkbxgJrHofE6sHvam\nxzjTBLHPdwkIyg3gcOme7DEYdg6gRoPzPBjVGFi0409DE18ZFElgriJ9Zo+GNWOM\n9rcfZIkZiEx3g9TerceLRjR1EyKbfhYDlIzgXn46wqJhu48vDrhZeKswoVCEQ1Ar\nR6+1IQKBgF78mW4Si37+WtTdsPE6tA8wzqXJJE7+6geD4tkVKovU25yGsyinihXV\nT3mhPCK7rJ5EoksAdrGKEmX5O1JqF0vwjUYNxtTLvpIraYKQSXlIaT3m4sI3Blco\nrfqFhHLw9qfYnmQQXhaXGBhZLQFYMKaYURg5/Z55Xmpmr//mK58m\n-----END RSA PRIVATE KEY-----\n";
    private static final String PUBKEY = "-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0vKu6oHF6wkA+gDdqhcs\nreLzlY9KLdNQBSSrlvWcZxmMglQU4oL017CvuTAqSWJmPlsO0c0fNy6yE+iOA2Sr\nhTM0RJ229fl6VDcFyHnG4grmcfslj2J3lCAssdON5tgrbRvt/FRrkF06x7rLCWiQ\nbcWZgt/Yj+ONbPHeHIImSzUkNkJCMMlwIv/l/Oz1gN6Pn6KZQxiASSqR5uS7v92v\nYXGpusLjvfLpFzCEBYbBEpguxmQFpfvyKZQHiimiEgqvWvOaNV8FKNi2/eELD3jr\ne6U0ng/eSlhP9FKrt109xdgw96ffVYK0o9o9IJm44RZFYvTjLIBGYI7uwaAgJK+r\nFQIDAQAB\n-----END PUBLIC KEY-----\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<Intent> aActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Log.i("MOBISEC", "Obtaining result");
                        try {
                        Intent data = result.getData();
                        Bundle bund = data.getExtras();
                        String flag = bund.getString("flag");
                        Log.i("MOBISEC", flag);
                        }catch(Exception e){
                            Log.i("MOBISEC", e.toString());
                        }
                    }
                });

        Intent intentA = buildIntent("B", "C", "Main-to-A/A-to-B");
        intentA.setAction("mobisec.intent.getflag");
        intentA.setClassName("com.mobisec.nojumpstarts", "com.mobisec.nojumpstarts.C");
        try {
            aActivityResultLauncher.launch(intentA);
        }catch(Exception e){
            Log.i("MOBISEC", e.toString());
        }
    }

    public static PKCS8EncodedKeySpec getPrivateKeySpec() throws Exception {
        StringBuilder pkcs8Lines = new StringBuilder();
        BufferedReader rdr = new BufferedReader(new StringReader(PRIVKEY));
        while (true) {
            String line = rdr.readLine();
            if (line != null) {
                pkcs8Lines.append(line);
            } else {
                String pkcs8Pem = pkcs8Lines.toString();
                byte[] pkcs8EncodedBytes = Base64.decode(pkcs8Pem.replace("-----BEGIN RSA PRIVATE KEY-----", "").replace("-----END RSA PRIVATE KEY-----", "").replaceAll("\\s+", ""), 0);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
                return keySpec;
            }
        }
    }

    public static X509EncodedKeySpec getPublicKeySpec() throws Exception {
        StringBuilder pkcs8Lines = new StringBuilder();
        BufferedReader rdr = new BufferedReader(new StringReader(PUBKEY));
        while (true) {
            String line = rdr.readLine();
            if (line != null) {
                pkcs8Lines.append(line);
            } else {
                String pkcs8Pem = pkcs8Lines.toString();
                byte[] pkcs8EncodedBytes = Base64.decode(pkcs8Pem.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s+", ""), 0);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pkcs8EncodedBytes);
                return keySpec;
            }
        }
    }

    public static PrivateKey getPrivateKey() throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = getPrivateKeySpec();
        PrivateKey privKey = kf.generatePrivate(keySpec);
        return privKey;
    }

    public static PublicKey getPublicKey() throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = getPublicKeySpec();
        PublicKey pubKey = kf.generatePublic(keySpec);
        return pubKey;
    }


    public static byte[] sign(String msg) throws Exception {
        byte[] msgbytes = msg.getBytes();
        PrivateKey privKey = getPrivateKey();
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initSign(privKey);
        s.update(msgbytes);
        byte[] signature = s.sign();
        return signature;
    }

    public static Intent buildIntent(String src, String dst, String chain) {
        String msg;
        Log.i("MOBISEC", "Creating Intent...");
        try {
            if (chain == null) {
                msg = src + "-to-" + dst;
            } else {
                msg = chain + "/" + src + "-to-" + dst;
            }
            byte[] sign = sign(msg);
            Intent i = new Intent();
            ComponentName cn = new ComponentName("com.mobisec.nojumpstarts", "com.mobisec.nojumpstarts." + dst);
            i.setComponent(cn);
            Log.i("MOBISEC", "Setting message: " + msg);
            i.putExtra("authmsg", msg);
            Log.i("MOBISEC", "Setting sign: " + Arrays.toString(sign));
            i.putExtra("authsign", sign);
            return i;
        } catch (Exception e) {
            return null;
        }
    }

}