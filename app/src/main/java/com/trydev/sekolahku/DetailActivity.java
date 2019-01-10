package com.trydev.sekolahku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.trydev.sekolahku.database.StudentDataSource;
import com.trydev.sekolahku.database.model.Student;

public class DetailActivity extends AppCompatActivity {

    private EditText detailNama;
    private EditText detailHp;
    private EditText detailGender;
    private EditText detailJenjang;
    private EditText detailHobi;
    private EditText detailAlamat;

    private StudentDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        long id = getIntent().getLongExtra("student_id",0);
        String nama = getIntent().getStringExtra("student_name");

        getSupportActionBar().setTitle(nama);

        dataSource = new StudentDataSource(this);

        detailNama = findViewById(R.id.detail_nama);
        detailHp = findViewById(R.id.detail_hp);
        detailGender = findViewById(R.id.detail_gender);
        detailJenjang = findViewById(R.id.detail_jenjang);
        detailHobi = findViewById(R.id.detail_hobi);
        detailAlamat = findViewById(R.id.detail_alamat);

        dataSource.open();
        Student student = dataSource.getStudentById(id);
        dataSource.close();

        detailNama.setText(student.getNamaDepan()+" "+student.getNamaBelakang());
        detailHp.setText(student.getHp());
        detailGender.setText(student.getGender());
        detailJenjang.setText(student.getJenjang());
        detailHobi.setText(student.getHobi());
        detailAlamat.setText(student.getAlamat());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
