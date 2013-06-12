package com.marakana.android.yamba.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.marakana.android.yamba.YambaContract;

class YambaDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE = "yamba.db";
    public static final int VERSION = 2;

    public static final String TABLE = YambaContract.Timeline.TABLE;
    public static final String COL_ID = YambaContract.Timeline.Columns.ID;
    public static final String COL_TIMESTAMP = YambaContract.Timeline.Columns.TIMESTAMP;
    public static final String COL_USER = YambaContract.Timeline.Columns.USER;
    public static final String COL_STATUS = YambaContract.Timeline.Columns.STATUS;

    public YambaDBHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE + "("
                        + COL_ID + " INTEGER PRIMARY KEY,"
                        + COL_TIMESTAMP + " INTEGER,"
                        + COL_USER + " TEXT,"
                        + COL_STATUS + " TEXT)");
     }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
