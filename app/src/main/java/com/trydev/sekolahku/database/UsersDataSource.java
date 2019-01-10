package com.trydev.sekolahku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.trydev.sekolahku.database.model.User;

public class UsersDataSource {

    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper helper;

    public UsersDataSource(Context context){
        helper = new DBHelper(context);
        this.context = context;
    }

    public void open() throws SQLException {
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    public void addUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());

        sqLiteDatabase.insert("Users", null, contentValues);
    }

    public User getUserByUsername(String username){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Users WHERE username=?", new String[]{username});
        if (cursor!= null){
            cursor.moveToFirst();
            User user = new User();
            user.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
            user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));

            cursor.close();

            return user;
        } else{
            return  null;
        }
    }
}
