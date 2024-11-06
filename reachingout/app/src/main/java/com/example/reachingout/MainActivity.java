package com.example.reachingout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread (){
            @Override
            public void run()
            {
                getRequest();
            }
        }.start();

        new Thread (){
            @Override
            public void run()
            {
                getRequestFlag();
            }
        }.start();

        new Thread (){
            @Override
            public void run()
            {
                postRequestFlag();
            }
        }.start();
    }

    private void getRequest(){
        StringBuilder content = new StringBuilder();
        String line;
        try {
            URL url = new URL("http://10.0.2.2:31337");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                while( (line = in.readLine()) != null) {
                    content.append(line);
                }
                in.close();
            } finally {
                Log.i("MOBISEC", content.toString());
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            Log.i("MOBISEC", e.toString());
            throw new RuntimeException(e);
        }
    }
    private void postRequestFlag(){
        StringBuilder content = new StringBuilder();
        String line;
        try {
            URL url = new URL("http://10.0.2.2:31337/flag");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setRequestMethod("POST");
                String urlParameters = "answer=" + URLEncoder.encode("9", "UTF-8")+ "&val1=" + URLEncoder.encode("3", "UTF-8") + "&oper=" +
                                        URLEncoder.encode("+", "UTF-8") + "&val2=" + URLEncoder.encode("6", "UTF-8");
                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                urlConnection.setDoOutput(true);
                OutputStream os = urlConnection.getOutputStream();
                os.write(postData);
                os.flush();
                os.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                while( (line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
                in.close();
                Log.i("MOBISEC", content.toString());
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            Log.i("MOBISEC", e.toString());
            throw new RuntimeException(e);
        }
    }
    private void getRequestFlag(){
        StringBuilder content = new StringBuilder();
        String line;
        try {
            URL url = new URL("http://10.0.2.2:31337/flag");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                while( (line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
                in.close();
            } finally {
                Log.i("MOBISEC", content.toString());
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            Log.i("MOBISEC", e.toString());
            throw new RuntimeException(e);
        }
    }
}
