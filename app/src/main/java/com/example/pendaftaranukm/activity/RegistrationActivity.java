package com.example.pendaftaranukm.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pendaftaranukm.MainActivity;
import com.example.pendaftaranukm.adapter.RegistrationAdapter;
import com.example.pendaftaranukm.api.ApiService;
import com.example.pendaftaranukm.api.RetrofitClient;
import com.example.pendaftaranukm.model.Registration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.pendaftaranukm.R;
import com.example.pendaftaranukm.model.Ukm;
import com.example.pendaftaranukm.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RegistrationAdapter registrationAdapter;
    private List<Registration> registrationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        registrationAdapter = new RegistrationAdapter(this, registrationList, this::approveOrRejectMember);
        recyclerView.setAdapter(registrationAdapter);

        loadUkmMembers();
    }

    private void loadUkmMembers() {
        String ukmName = getIntent().getStringExtra("UKM_NAME");
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<List<Registration>> call = apiService.getRegistrationsByUkmName(ukmName);
        call.enqueue(new Callback<List<Registration>>() {
            @Override
            public void onResponse(Call<List<Registration>> call, Response<List<Registration>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    registrationList.clear();
                    registrationList.addAll(response.body());
                    registrationAdapter.notifyDataSetChanged();
                    if (registrationList.isEmpty()) {
                        Toast.makeText(RegistrationActivity.this, "Belum ada pendaftar", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this, "Gagal memuat daftar pendaftar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Registration>> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void approveOrRejectMember(Registration registration, boolean approve) {
        String status = approve ? "APPROVED" : "REJECTED";
        registration.setStatus(status);
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<Registration> call = apiService.createRegistration(registration);
        call.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "Status diperbarui", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Gagal memperbarui status", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}