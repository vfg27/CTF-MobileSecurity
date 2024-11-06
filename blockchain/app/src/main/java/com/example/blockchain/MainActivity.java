package com.example.blockchain;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean aux = false;
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 256; k++) {
                    byte[] combination = {(byte) (i - 128), (byte) (j - 128), (byte) (k - 128)};
                    Log.i("MOBISEC", printBytes(combination));
                    byte[] currKey = null;
                    try {
                        currKey = hash(combination);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    List<byte[]> myList = new ArrayList<byte[]>();
                    myList.add(currKey);
                    for (int l = 0; l < 9; l++) {
                        try {
                            myList.add(hash(currKey));
                            currKey=myList.get(l+1);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    String encryptedHex = "0eef68c5ef95b67428c178f045e6fc8389b36a67bbbd800148f7c285f938a24e696ee2925e12ecf7c11f35a345a2a142639fe87ab2dd7530b29db87ca71ffda2af558131d7da615b6966fb0360d5823b79c26608772580bf14558e6b7500183ed7dfd41dbb5686ea92111667fd1eff9cec8dc29f0cfe01e092607da9f7c2602f5463a361ce5c83922cb6c3f5b872dcc088eb85df80503c92232bf03feed304d669ddd5ed1992a26674ecf2513ab25c20f95a5db49fdf6167fda3465a74e0418b2ea99eb2673d4c7e1ff7c4921c4e2d7b";
                    byte[] currPt = fromHex(encryptedHex);
                    Log.i("MOBISEC", printBytes(currPt));
                    for (int m = 9; m >= 0; m--) {
                        byte[] newPt = new byte[0];
                        try {
                            newPt = decrypt(currPt, myList.get(m));
                        } catch (Exception e) {
                            Log.i("MOBISEC", e.toString());
                            //throw new RuntimeException(e);
                        }
                        currPt = newPt;
                    }
                    String decodedString = null;
                    try {
                        decodedString = new String(currPt, "UTF-8");
                    } catch (UnsupportedEncodingException ex) {
                        throw new RuntimeException(ex);
                    }
                    Log.i("MOBISEC", decodedString);
                    if (decodedString.startsWith("MOBISEC{")) {
                        Log.i("MOBISEC", decodedString);
                        aux = true ;
                        break;
                    }
                }
                if (aux) {
                    break;
                }
            }
            if (aux) {
                break;
            }
        }
    }

    public static String printBytes(byte[] bytes) {
        String res = "";
        for (byte b : bytes) {
            res= res + b;
        }
        return res;
    }
    public static byte[] hash(byte[] in) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(in);
        return md.digest();
    }

    public static String toHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 255);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] decrypt(byte[] in, byte[] key) throws Exception {
        Key aesKey = new SecretKeySpec(key, "AES");
        Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, aesKey);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, decryptCipher);
        cipherOutputStream.write(in);
        cipherOutputStream.flush();
        cipherOutputStream.close();
        byte[] out = outputStream.toByteArray();
        return out;
    }

    public static byte[] fromHex(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

}