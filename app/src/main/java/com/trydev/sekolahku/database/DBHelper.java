package com.trydev.sekolahku.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "sekolahku";
    private static final int DATABASE_VERSION = 3;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "Create TABLE Student(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nama_depan TEXT," +
                        "nama_belakang TEXT," +
                        "no_hp TEXT," +
                        "gender TEXT," +
                        "jenjang TEXT," +
                        "hobi TEXT," +
                        "alamat TEXT);"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE Users(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT," +
                        "password TEXT);"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Student");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(sqLiteDatabase);
    }

}
