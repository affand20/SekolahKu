package com.trydev.sekolahku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.trydev.sekolahku.database.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDataSource {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper helper;

    public StudentDataSource(Context context){
        helper = new DBHelper(context);
        this.context = context;
    }

    public void open() throws SQLException{
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    public boolean addStudent(Student student){
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_depan", student.getNamaDepan());
        contentValues.put("nama_belakang", student.getNamaBelakang());
        contentValues.put("no_hp", student.getHp());
        contentValues.put("gender", student.getGender());
        contentValues.put("jenjang", student.getJenjang());
        contentValues.put("hobi", student.getHobi());
        contentValues.put("alamat", student.getAlamat());

        sqLiteDatabase.insert("Student", null, contentValues);
        return true;
    }

    public ArrayList<Student> getAllStudent(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Student", null);
        cursor.moveToFirst();

        ArrayList<Student> dataSiswa = new ArrayList<>();

        Student student;

        if (cursor.getCount()>0){
            while (!cursor.isAfterLast()){
                student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                student.setNamaDepan(cursor.getString(cursor.getColumnIndexOrThrow("nama_depan")));
                student.setNamaBelakang(cursor.getString(cursor.getColumnIndexOrThrow("nama_belakang")));
                student.setHp(cursor.getString(cursor.getColumnIndexOrThrow("no_hp")));
                student.setGender(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
                student.setJenjang(cursor.getString(cursor.getColumnIndexOrThrow("jenjang")));
                student.setHobi(cursor.getString(cursor.getColumnIndexOrThrow("hobi")));
                student.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow("alamat")));

                dataSiswa.add(student);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return dataSiswa;
    }

    public Student getStudentById(long id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM student WHERE id=?", new String[]{Long.toString(id)});
        cursor.moveToFirst();

        Student student = new Student();
        student.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        student.setNamaDepan(cursor.getString(cursor.getColumnIndexOrThrow("nama_depan")));
        student.setNamaBelakang(cursor.getString(cursor.getColumnIndexOrThrow("nama_belakang")));
        student.setHp(cursor.getString(cursor.getColumnIndexOrThrow("no_hp")));
        student.setGender(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
        student.setJenjang(cursor.getString(cursor.getColumnIndexOrThrow("jenjang")));
        student.setHobi(cursor.getString(cursor.getColumnIndexOrThrow("hobi")));
        student.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow("alamat")));

        cursor.close();

        return student;
    }

    public ArrayList<Student> searchStudent(String keyword){
        ArrayList<Student> listStudents = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Student WHERE nama_depan LIKE ? OR nama_belakang LIKE ?", new String[]{"%"+keyword+"%","%"+keyword+"%"});
        cursor.moveToFirst();

        if (cursor.getCount()>0){
            while (!cursor.isAfterLast()){
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                student.setNamaDepan(cursor.getString(cursor.getColumnIndexOrThrow("nama_depan")));
                student.setNamaBelakang(cursor.getString(cursor.getColumnIndexOrThrow("nama_belakang")));
                student.setHp(cursor.getString(cursor.getColumnIndexOrThrow("no_hp")));
                student.setGender(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
                student.setJenjang(cursor.getString(cursor.getColumnIndexOrThrow("jenjang")));
                student.setHobi(cursor.getString(cursor.getColumnIndexOrThrow("hobi")));
                student.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow("alamat")));

                listStudents.add(student);

                cursor.moveToNext();
            }
        }
        cursor.close();
        return listStudents;
    }

    public void deleteStudent(Student student){
        sqLiteDatabase.delete("student", "id=?", new String[]{Long.toString(student.getId())});
    }

    public void updateStudent(Student student){
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_depan", student.getNamaDepan());
        contentValues.put("nama_belakang", student.getNamaBelakang());
        contentValues.put("no_hp", student.getHp());
        contentValues.put("gender", student.getGender());
        contentValues.put("jenjang", student.getJenjang());
        contentValues.put("hobi", student.getHobi());
        contentValues.put("alamat", student.getAlamat());

        sqLiteDatabase.update("student", contentValues, "id=?", new String[]{Long.toString(student.getId())});
    }

}
