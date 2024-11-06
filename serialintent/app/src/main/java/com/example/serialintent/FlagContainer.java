package com.example.serialintent;

import android.util.Base64;
import android.util.Log;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class FlagContainer implements Serializable {
    private String[] parts;
    private ArrayList<Integer> perm;

    public FlagContainer(String[] parts, ArrayList<Integer> perm) {
        this.parts = parts;
        this.perm = perm;
    }

    public void getParts(){
        int n = parts.length;
        for (int i=0; i<n; i++) {
            byte[] flagBytes = Base64.decode(parts[i], Base64.DEFAULT);
            Log.i("MOBISEC", new String(flagBytes, Charset.defaultCharset()));
        }
    }

    public void getPerms(){
        int n = perm.size();
        for (int i=0; i<n; i++) {
            Log.i("MOBISEC", perm.get(i).toString());
        }
    }

    public String getFlag() {
        int n = parts.length;
        int i;
        String b64 = new String();
        for (i=0; i<n; i++) {
            b64 += parts[perm.get(i)];
        }
        byte[] flagBytes = Base64.decode(b64, Base64.DEFAULT);
        String flag = new String(flagBytes, Charset.defaultCharset());

        return flag;
    }
}
