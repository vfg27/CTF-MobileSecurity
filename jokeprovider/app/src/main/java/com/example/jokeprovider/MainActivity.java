package com.example.jokeprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentProviderClient;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    static final String CREATE_TABLE =
            " CREATE TABLE joke" +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " author TEXT NOT NULL, " +
                    " joke TEXT NOT NULL);";

    static final String PROVIDER_NAME = "com.mobisec.provider.Joke";
    static final String URL = "content://" + PROVIDER_NAME + "/jokes";

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cursor cursor = getContentResolver().query(Uri.parse(URL), new String[]{"joke"}, "author = ?", new String[]{"reyammer"}, null);
        String res = "";
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Log.i("MOBISEC", cursor.getString(cursor.getColumnIndex("joke")).toString());
                res += cursor.getString(cursor.getColumnIndex("joke")).toString();
            } while (cursor.moveToNext());
        }
        Log.i("MOBISEC", res);


    }
}