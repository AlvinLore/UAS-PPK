package com.example.pendaftaranukm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pendaftaranukm.MainActivity;
import com.example.pendaftaranukm.api.ApiService;
import com.example.pendaftaranukm.api.RetrofitClient;
import com.example.pendaftaranukm.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.pendaftaranukm.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText etNim, etName, etClass, etEmail, etPassword;
    private Button btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNim = findViewById(R.id.etNim);
        etName = findViewById(R.id.etName);
        etClass = findViewById(R.id.etClass);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> registerUser());

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String nim = etNim.getText().toString();
        String name = etName.getText().toString();
        String userClass = etClass.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User();
        user.setNim(nim);
        user.setName(name);
        user.setUserClass(userClass);
        user.setEmail(email);
        user.setPassword(password);

        ApiService apiService = RetrofitClient.getApiService(this);
        Call<User> call = apiService.registerUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Membuat akun selesai, silahkan login", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}