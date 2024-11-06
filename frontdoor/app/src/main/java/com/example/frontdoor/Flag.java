package com.example.frontdoor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/* loaded from: classes2.dex */
class Flag {
    private static String sUrl = "http://10.0.2.2:31337/getflag";
    private static boolean debug = false;

    Flag() {
    }

    public static String getFlag(String username, String password) throws Exception {
        String urlParameters;
        if (debug) {
            urlParameters = "username=testuser&password=passtestuser123";
        } else {
            urlParameters = "username=" + username + "&password=" + password;
        }
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        String request = sUrl + "?" + urlParameters;
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String content = "";
        while (true) {
            String line = rd.readLine();
            if (line != null) {
                content = content + line + "\n";
            } else {
                return content;
            }
        }
    }
}