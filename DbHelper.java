package com.example.storageexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EMS.db";
    private static final int Version = 1;
    public static final String TABLE_NAME = "tblEmployee";
    public static final String Emp_ID = "_id";
    public static final String Emp_Name = "EmpName";
    public static final String Contact_No = "ContactNo";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + Emp_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Emp_Name +
            " TEXT NOT NULL," + Contact_No + " TEXT);";
    public DbHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
