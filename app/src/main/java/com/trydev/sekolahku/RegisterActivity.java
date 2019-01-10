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

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextPasswordValidasi;
    private Button buttonRegister;
    private TextView textViewLogin;

    private UsersDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.edittext_username);
        editTextPassword = findViewById(R.id.edittext_password);
        editTextPasswordValidasi = findViewById(R.id.edittext_password_validasi);
        buttonRegister = findViewById(R.id.button_register);
        textViewLogin = findViewById(R.id.textview_login);

        dataSource = new UsersDataSource(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){

                    User user = new User();
                    user.setUsername(editTextUsername.getText().toString());
                    user.setPassword(editTextPassword.getText().toString());

                    dataSource.open();
                    dataSource.addUser(user);
                    dataSource.close();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
        if (editTextPasswordValidasi.getText().toString().isEmpty()){
            editTextPasswordValidasi.setError("Password wajib diisi sekali lagi");
            editTextPasswordValidasi.requestFocus();
            return false;
        }
        if (!editTextPasswordValidasi.getText().toString().equals(editTextPassword.getText().toString())){
            editTextPasswordValidasi.setError("Password tidak cocok");
            editTextPasswordValidasi.requestFocus();
            return false;
        }
        return true;
    }
}
