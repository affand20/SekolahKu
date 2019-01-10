package com.trydev.sekolahku;

import android.service.autofill.RegexValidator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.trydev.sekolahku.adapter.StudentAdapter;
import com.trydev.sekolahku.database.DBHelper;
import com.trydev.sekolahku.database.StudentDataSource;
import com.trydev.sekolahku.database.model.Student;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class FormActivity extends AppCompatActivity {

    private EditText namaDepan;
    private EditText namaBelakang;
    private EditText nomorHp;
    private EditText editTextAlamat;
    private RadioGroup genderGroup;
    private RadioButton radioButtonLakiLaki;
    private RadioButton radioButtonPerempuan;
    private Spinner jenjang;
    private CheckBox checkboxMembaca;
    private CheckBox checkboxMenulis;
    private CheckBox checkboxMenggambar;
    private Button submitButton;

    private String jenjangTerpilih;
    private String genderTerpilih;
    private ArrayList<String> hobiTerpilih = new ArrayList<>();

    private StudentDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Tambah Siswa");

        dataSource = new StudentDataSource(this);

        namaDepan = findViewById(R.id.edittext_nama_depan);
        namaBelakang = findViewById(R.id.edittext_nama_belakang);
        nomorHp = findViewById(R.id.edittext_nomor_hp);
        editTextAlamat = findViewById(R.id.edittext_alamat);
        genderGroup = findViewById(R.id.radiogroup_gender);
        radioButtonLakiLaki = findViewById(R.id.radiobutton_laki);
        radioButtonPerempuan = findViewById(R.id.radiobutton_perempuan);
        jenjang = findViewById(R.id.spinner_jenjang);
        checkboxMembaca = findViewById(R.id.checkbox_membaca);
        checkboxMenulis = findViewById(R.id.checkbox_menulis);
        checkboxMenggambar = findViewById(R.id.checkbox_menggambar);

        final long id = getIntent().getLongExtra("student_id", 0);

        if (id>0){
            getSupportActionBar().setTitle("Edit Siswa");
            dataSource.open();
            Student student = dataSource.getStudentById(id);
            dataSource.close();

            if (student!=null){
                namaDepan.setText(student.getNamaDepan());
                namaBelakang.setText(student.getNamaBelakang());
                nomorHp.setText(student.getHp());
                editTextAlamat.setText(student.getAlamat());

                if (student.getGender().equals("Laki-laki")){
                    radioButtonLakiLaki.setChecked(true);
                } else if (student.getGender().equals("Perempuan")){
                    radioButtonPerempuan.setChecked(true);
                }

                if (student.getHobi().contains("Menulis")){
                    checkboxMenulis.setChecked(true);
                }
                if (student.getHobi().contains("Menggambar")){
                    checkboxMenggambar.setChecked(true);
                }
                if (student.getHobi().contains("Membaca")){
                    checkboxMembaca.setChecked(true);
                }

                String jenjangArray[] = getResources().getStringArray(R.array.spinner_entry);
                for (int i = 0; i < jenjangArray.length; i++) {
                    if (student.getJenjang().equals(jenjangArray[i])){
                        jenjang.setSelection(i);
                        break;
                    }
                }
            }
        }


        submitButton = findViewById(R.id.button_submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    hobiTerpilih.clear();
                    String nama = namaDepan.getText().toString().concat(" "+namaBelakang.getText().toString());
                    String hp = nomorHp.getText().toString();

                    switch (genderGroup.getCheckedRadioButtonId()){
                        case R.id.radiobutton_laki :
                            genderTerpilih = radioButtonLakiLaki.getText().toString();
                            break;
                        case R.id.radiobutton_perempuan :
                            genderTerpilih = radioButtonPerempuan.getText().toString();
                            break;
                    }

                    jenjangTerpilih = jenjang.getSelectedItem().toString();

                    if (checkboxMenggambar.isChecked()){
                        hobiTerpilih.add(checkboxMenggambar.getText().toString());
                    }
                    if (checkboxMembaca.isChecked()){
                        hobiTerpilih.add(checkboxMembaca.getText().toString());
                    }
                    if (checkboxMenulis.isChecked()){
                        hobiTerpilih.add(checkboxMenulis.getText().toString());
                    }

                    String alamat = editTextAlamat.getText().toString();
//                    Toast.makeText(
//                            FormActivity.this,
//                            "Nama : "+nama+"\n" +
//                                    "HP : "+hp+"\n" +
//                                    "Gender : "+genderTerpilih+"\n" +
//                                    "Jenjang : "+jenjangTerpilih+"\n" +
//                                    "Hobi : "+TextUtils.join(",", hobiTerpilih)+"\n" +
//                                    "Alamat : "+alamat,
//                            Toast.LENGTH_SHORT).show();
                    Student student = new Student();
                    student.setNamaDepan(namaDepan.getText().toString());
                    student.setNamaBelakang(namaBelakang.getText().toString());
                    student.setHp(nomorHp.getText().toString());
                    student.setGender(genderTerpilih);
                    student.setJenjang(jenjangTerpilih);
                    student.setHobi(TextUtils.join(",", hobiTerpilih));
                    student.setAlamat(editTextAlamat.getText().toString());

                    dataSource.open();
                    if (id>0){
                        student.setId(id);
                        dataSource.updateStudent(student);
                    } else{
                        dataSource.addStudent(student);
                    }

                    dataSource.close();

                    finish();
                }

            }
        });

    }

    private boolean validate() {
        if (namaDepan.getText().toString().isEmpty()){
            namaDepan.setError("Nama Depan wajib diisi");
            namaDepan.requestFocus();
            return false;
        }
        if (namaBelakang.getText().toString().isEmpty()){
            namaBelakang.setError("Nama Belakang wajib diisi");
            namaBelakang.requestFocus();
            return false;
        }
        if (nomorHp.getText().toString().isEmpty()){
            nomorHp.setError("Nomor Hp wajib diisi");
            nomorHp.requestFocus();
            return false;
        }
        if (nomorHp.getText().toString().length()>12){
            nomorHp.setError("Nomor Hp harus dibawah 12 karakter");
            nomorHp.requestFocus();
            return false;
        }
        if (genderGroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(this, "Gender wajib dipilih", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextAlamat.getText().toString().isEmpty()){
            editTextAlamat.setError("Alamat wajib diisi");
            editTextAlamat.requestFocus();
            return false;
        }
        return true;
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
