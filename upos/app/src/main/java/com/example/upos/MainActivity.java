package com.example.upos;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.internal.view.SupportMenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static long[][] m = (long[][]) Array.newInstance(long.class, 256, 256);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            lm(m);
        } catch (Exception e) {
            Log.i("MOBISEC", e.toString());
        }
        //printLongArray(m);
        //Log.i("MOBISEC", "Letter: " + "MOBISEC{JDBOBDOVINWEVNPOWNONVPEFN}".charAt(0));
        /*
        Log.i("MOBISEC", "s1: " + dec("bHM="));
        Log.i("MOBISEC", "s2: " + dec("L3Byb2M="));
        Log.i("MOBISEC", "s3: " + dec("Y2F0"));
        Log.i("MOBISEC", "s4: " + dec("ZnJpZGE="));
        Log.i("MOBISEC", "s5: " + dec("Y29tLmFuZHJvaWQudmVuZGluZw=="));
        Log.i("MOBISEC", "s6: " + dec("018a94a01edcfd1c8121f56dd36a412e62b3dd8b"));
        Log.i("MOBISEC", "s7: " + dec("YW5kcm9pZC5vcy5EZWJ1Zw=="));
        Log.i("MOBISEC", "s8: " + dec("aXNEZWJ1Z2dlckNvbm5lY3RlZA=="));
        Log.i("MOBISEC", "ec(ls /proc): " + ec("ls /proc"));
         */
       /*
        for (int iVar7 = 0; iVar7 < 0x1e; iVar7 = iVar7 + 1) {
         Log.i("MOBISEC", valueOf(ip(iVar7)));
         Log.i("MOBISEC", valueOf(ip(iVar7)));
         Log.i("MOBISEC", valueOf(ip(iVar7)));
        }*/
        Streamer stream = new Streamer();
        stream.step();
        stream.step();
        stream.step();
        stream.step();
        stream.step();
        stream.step();
        stream.step();
        stream.step();
        stream.step();
        stream.step();
        stream.step();
        int aux = stream.step();
        String [] res = new String[30];
        boolean fl = false;
        boolean[] bools = stream.getLFSR();
        int tap = stream.getTap();
        Streamer stream2 = copyStreamer(bools, tap);
        for (int k=0; k<30; k++) {
            for (int j = 33; j <= 126; j++) {
                for (int l = 33; l <= 126; l++) {
                    bools = stream.getLFSR();
                    tap = stream.getTap();
                    stream2 = copyStreamer(bools ,tap);
                    //Log.i("MOBISEC", "ACTUAL: " + Arrays.toString(stream2.getLFSR()));
                    String test = "" + (char) j + (char) l;
                    if (ip(k)) {
                        for (int p = 0; p < k; p++) {
                            stream2.step();
                        }
                    }
                    int j2 = stream2.g2();
                    int mX = j2 & 255;
                    int mY = (stream2.g2() & 65280) >> 8;
                    if (sq(r(test)) != m[mX][mY]) {
                        //Log.i("MOBISEC","WRONG");
                        //Log.i("MOBISEC", "ACTUAL2: " + Arrays.toString(stream2.getLFSR()));
                        //Log.i("MOBISEC", "ACTUAL4: " + Arrays.toString(stream.getLFSR()));
                        bools = stream.getLFSR();
                        tap = stream.getTap();
                        stream2 = copyStreamer(bools ,tap);
                        //Log.i("MOBISEC", "ACTUAL3: " + Arrays.toString(stream2.getLFSR()));
                    } else {
                        //Log.i("MOBISEC", "GOOD");
                        //Log.i("MOBISEC", test);
                        res[k] = test;
                        fl = true;
                        break;
                    }
                }
                if (fl) {
                    fl = false;
                    break;
                }
            }
            bools = stream2.getLFSR();
            tap = stream2.getTap();
            stream = copyStreamer(bools ,tap);
        }
        String result = "MOBISEC{";
        for(int m=0; m<res.length;m++){
            result += res[m];
            if(m==res.length-1){
                result += "}";
            }
        }

        String hRes = h(result);
        if(hRes.equals("4193d9b72a5c4805e9a5cc739f8a8fc23b2890e13b83bb887d96f86c30654a12")){
            Log.i("MOBISEC", "The flag is: " + result);
        }else{
            Log.i("MOBISEC", "FLAG NOT FOUND");
            Log.i("MOBISEC", result);
            Log.i("MOBISEC", hRes);
        }
        /*
        String stAux = "Is";
        if (ip(0)) {
            for (int j = 0; j < 0; j++) {
                stream.step();
            }
        }
        int j2 = stream.g2();
        int mX = j2 & 255;
        int mY = (stream.g2() & 65280) >> 8;
        if (sq(r(stAux)) != m[mX][mY]) {
            Log.i("MOBISEC","WRONG");
        }else{
            Log.i("MOBISEC","GOOD");
        }
        */
    }

    private static String h(String flag) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(flag.getBytes());
            byte[] digest = md.digest();
            return th(digest);
        } catch (Exception e) {
            return null;
        }
    }

    public static String th(byte[] bytes) {
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

    public Streamer copyStreamer(boolean[] data, int tap){
        String res1 = "";
        for (int i = 0; i< data.length;i++){
            if(data[i]){
                res1 = res1 + "1";
            }else{
                res1 = res1 + "0";
            }
        }
        int res2 = (data.length - 1) - tap;
        return new Streamer(res1, res2);
    }
    public static String r(String s) {
        String out = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 's') {
                c = (char) (c + 7);
            } else if (c >= 'A' && c <= 'S') {
                c = (char) (c + 7);
            } else if (c >= 't' && c <= 'z') {
                c = (char) (c - 19);
            } else if (c >= 'T' && c <= 'Z') {
                c = (char) (c - 19);
            }
            out = out + c;
        }
        return out;
    }

    public static long sq(String a) {
        int n = (a.charAt(0) + (a.charAt(1) << '\b')) & 65535;
        long n2 = (long) Math.pow(n, 2.0d);
        return n2;
    }


    public static boolean ip(int x) {
        for (int i = 2; i < x; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static String ec(String cmd) {
        String out = new String();
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            InputStream stdout = p.getInputStream();
            byte[] buffer = new byte[102400];
            while (true) {
                int read = stdout.read(buffer);
                if (read > 0 && read <= 102400) {
                    String line = new String(buffer, 0, read);
                    out = out + line;
                } else if (stdout.available() <= 0) {
                    break;
                }
            }
            stdout.close();
        } catch (Exception e) {
        }
        return out;
    }

    public static String dec(String x) {
        return new String(Base64.decode(x, 0));
    }

    public static void printLongArray(long[][] array) {
        for (int i = 0; i < array.length; i++) {
            Log.i("MOBISEC", Arrays.toString(array[i]));
        }
    }

    private void lm(long[][] matrix) throws Exception {
        BufferedReader reader = null;
        try {
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(this.getAssets().open("lotto.dat")));
            int rowIdx = 0;
            while (true) {
                String row = reader2.readLine();
                if (row != null) {
                    String[] elems = row.split(" ");
                    int colIdx = 0;
                    for (String elem : elems) {
                        long e = Long.parseLong(elem);
                        matrix[rowIdx][colIdx] = e;
                        colIdx++;
                    }
                    if (colIdx != 256) {
                        throw new Exception("error");
                    }
                    rowIdx++;
                } else if (rowIdx != 256) {
                    throw new Exception("error");
                } else {
                    reader2.close();
                    return;
                }
            }
        } catch (Throwable th) {
            if (0 != 0) {
                reader.close();
            }
            throw th;
        }
    }

}