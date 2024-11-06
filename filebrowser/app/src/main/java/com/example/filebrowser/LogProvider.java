package com.example.filebrowser;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class LogProvider extends ContentProvider {
    static final String ARG = "arg";
    static final String CREATE_TABLE = " CREATE TABLE log (id INTEGER PRIMARY KEY AUTOINCREMENT,  oper TEXT NOT NULL,  arg TEXT NOT NULL);";
    static final String DATABASE_NAME = "LogDb";
    static final int DATABASE_VERSION = 1;
    static final int ENTRY = 1;
    static final int ENTRY_ID = 2;
    static final String ID = "id";
    static final String OPER = "oper";
    static final String PROVIDER_NAME = "com.mobisec.provider.Log";
    static final String TABLE_NAME = "log";
    private static HashMap<String, String> infoMap;
    private SQLiteDatabase database;
    DBHelper dbHelper;
    static final String URL = "content://com.mobisec.provider.Log/log";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final UriMatcher uriMatcher = new UriMatcher(-1);

    static {
        uriMatcher.addURI(PROVIDER_NAME, TABLE_NAME, 1);
        uriMatcher.addURI(PROVIDER_NAME, "log/#", 2);
    }

    /* loaded from: classes2.dex */
    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, LogProvider.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(LogProvider.CREATE_TABLE);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String name = DBHelper.class.getName();
            Log.w(name, "Upgrading database from version " + oldVersion + " to " + newVersion + ". Old data will be destroyed");
            db.execSQL("DROP TABLE IF EXISTS log");
            onCreate(db);
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        String str;
        int match = uriMatcher.match(uri);
        if (match == 1) {
            count = this.database.delete(TABLE_NAME, selection, selectionArgs);
        } else if (match == 2) {
            String id = uri.getLastPathSegment();
            SQLiteDatabase sQLiteDatabase = this.database;
            StringBuilder sb = new StringBuilder();
            sb.append("id = ");
            sb.append(id);
            if (TextUtils.isEmpty(selection)) {
                str = "";
            } else {
                str = " AND (" + selection + ')';
            }
            sb.append(str);
            count = sQLiteDatabase.delete(TABLE_NAME, sb.toString(), selectionArgs);
        } else {
            throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);
        if (match != 1) {
            if (match == 2) {
                return "vnd.android.cursor.item/vnd.example.log";
            }
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return "vnd.android.cursor.dir/vnd.example.log";
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        long row = this.database.insert(TABLE_NAME, "", values);
        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Context context = getContext();
        this.dbHelper = new DBHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
        if (this.database == null) {
            return false;
        }
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        int match = uriMatcher.match(uri);
        if (match == 1) {
            queryBuilder.setProjectionMap(infoMap);
        } else if (match == 2) {
            queryBuilder.appendWhere("id=" + uri.getLastPathSegment());
        } else {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = OPER;
        }
        Cursor cursor = queryBuilder.query(this.database, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}