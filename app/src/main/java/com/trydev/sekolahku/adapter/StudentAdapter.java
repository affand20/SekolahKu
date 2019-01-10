package com.trydev.sekolahku.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trydev.sekolahku.R;
import com.trydev.sekolahku.database.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {

    private ArrayList<Student> data;
    Context context;

    public StudentAdapter(ArrayList<Student> data, Context context){
        super(context, R.layout.student_item, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        View view = convertView;

        if (view==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.student_item, null);
        }

        TextView namaSiswa = view.findViewById(R.id.nama_siswa);
        TextView genderSiswa = view.findViewById(R.id.gender_siswa);
        TextView jenjangSiswa = view.findViewById(R.id.jenjang_siswa);
        TextView noHpSiswa = view.findViewById(R.id.no_hp_siswa);

        Student student = getItem(position);
        namaSiswa.setText(student.getNamaDepan()+" "+student.getNamaBelakang());
        genderSiswa.setText(student.getGender());
        jenjangSiswa.setText(student.getJenjang());
        noHpSiswa.setText(student.getHp());

        return view;
    }

    public void updateDataSiswa(ArrayList<Student> newData){
        data.clear();
        data.addAll(newData);
        this.notifyDataSetChanged();
    }
}
