package com.trydev.sekolahku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trydev.sekolahku.database.UsersDataSource;
import com.trydev.sekolahku.database.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;

    private UsersDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.edittext_username);
        editTextPassword = findViewById(R.id.edittext_password);
        buttonLogin = findViewById(R.id.button_login);
        textViewRegister = findViewById(R.id.textview_register);

        dataSource = new UsersDataSource(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    dataSource.open();
                    User user;
                    try{
                        user = dataSource.getUserByUsername(editTextUsername.getText().toString());
                    } catch (Exception e){
                        user = null;
                        Toast.makeText(LoginActivity.this, "Akun tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }

                    dataSource.close();

                    if (user!=null){
                        if (editTextPassword.getText().toString().equals(user.getPassword())){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this, "Selamat datang, "+user.getUsername(), Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(LoginActivity.this, "Password tidak cocok", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(LoginActivity.this, "Akun tidak ditemukan, silakan daftar terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validate() {
        if (editTextUsername.getText().toString().isEmpty()){
            editTextUsername.setError("Username wajib diisi");
            editTextUsername.requestFocus();
            return false;
        }
        if (editTextPassword.getText().toString().isEmpty()){
            editTextPassword.setError("Password wajib diisi");
            editTextPassword.requestFocus();
            return false;
        }
        return true;
    }
}
