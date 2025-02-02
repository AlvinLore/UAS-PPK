package com.example.pendaftaranukm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pendaftaranukm.api.ApiService;
import com.example.pendaftaranukm.api.RetrofitClient;
import com.example.pendaftaranukm.auth.ChangePasswordRequest;
import com.example.pendaftaranukm.auth.SessionManager;
import com.example.pendaftaranukm.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.pendaftaranukm.R;

public class ProfileActivity extends AppCompatActivity {
    private EditText etNim, etName, etClass, etEmail, etNewPassword, etOldPassword;
    private Button btnUpdateProfile, btnBack, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etNim = findViewById(R.id.etNim);
        etName = findViewById(R.id.etName);
        etClass = findViewById(R.id.etClass);
        etEmail = findViewById(R.id.etEmail);
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnBack = findViewById(R.id.btnBack);
        btnLogout = findViewById(R.id.btnLogout);

        loadProfile();

        btnUpdateProfile.setOnClickListener(v -> updateProfile());
        btnBack.setOnClickListener(v -> finish()); // Kembali ke activity sebelumnya
        btnLogout.setOnClickListener(v -> logout()); // Logout
    }

    private void loadProfile() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<User> call = apiService.getProfile();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    etNim.setText(user.getNim());
                    etName.setText(user.getName());
                    etClass.setText(user.getUserClass());
                    etEmail.setText(user.getEmail());
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {
        String nim = etNim.getText().toString();
        String name = etName.getText().toString();
        String userClass = etClass.getText().toString();
        String email = etEmail.getText().toString();
        String oldPassword = etOldPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();

        // Update profil (nama, kelas, email)
        User user = new User();
        user.setNim(nim);
        user.setName(name);
        user.setUserClass(userClass);
        user.setEmail(email);

        ApiService apiService = RetrofitClient.getApiService(this);
        Call<User> call = apiService.updateProfile(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(oldPassword, newPassword);
            Call<Void> changePasswordCall = apiService.changePassword(changePasswordRequest);
            changePasswordCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Password Update Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void logout() {
        // Hapus token dan arahkan ke LoginActivity
        SessionManager tokenManager = new SessionManager(this);
        tokenManager.clearToken();

        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}