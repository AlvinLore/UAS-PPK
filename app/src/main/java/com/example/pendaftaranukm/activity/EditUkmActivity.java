package com.example.pendaftaranukm.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pendaftaranukm.api.ApiService;
import com.example.pendaftaranukm.api.RetrofitClient;
import com.example.pendaftaranukm.model.Ukm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.pendaftaranukm.R;

import java.util.List;

public class EditUkmActivity extends AppCompatActivity {
    private EditText etUkmName, etUkmDescription;
    private Button btnUpdateUkm;
    private Ukm ukm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ukm);

        etUkmName = findViewById(R.id.etUkmName);
        etUkmDescription = findViewById(R.id.etUkmDescription);
        btnUpdateUkm = findViewById(R.id.btnUpdateUkm);

        // Ambil data UKM dari intent
        long ukmId = getIntent().getLongExtra("UKM_ID", -1);
        String ukmName = getIntent().getStringExtra("UKM_NAME");
        if (ukmId != -1) {
            loadUkmDetails(ukmName);
        }

        btnUpdateUkm.setOnClickListener(v -> updateUkm());
    }

    private void loadUkmDetails(String ukmName) {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<List<Ukm>> call = apiService.getUkmByName(ukmName);
        call.enqueue(new Callback<List<Ukm>>() {
            @Override
            public void onResponse(Call<List<Ukm>> call, Response<List<Ukm>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    // Ambil UKM pertama dari daftar
                    ukm = response.body().get(0);
                    etUkmName.setText(ukm.getName());
                    etUkmDescription.setText(ukm.getDescription());
                } else {
                    Toast.makeText(EditUkmActivity.this, "Gagal mendapatkan ID UKM", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ukm>> call, Throwable t) {
                Toast.makeText(EditUkmActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUkm() {
        String name = etUkmName.getText().toString();
        String description = etUkmDescription.getText().toString();

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Nama dan deskripsi UKM harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update data UKM
        ukm.setName(name);
        ukm.setDescription(description);

        // Kirim data UKM yang diperbarui ke API
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<Ukm> call = apiService.updateUkm(ukm.getId(), ukm);
        call.enqueue(new Callback<Ukm>() {
            @Override
            public void onResponse(Call<Ukm> call, Response<Ukm> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditUkmActivity.this, "UKM berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditUkmActivity.this, "Gagal memperbarui UKM", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ukm> call, Throwable t) {
                Toast.makeText(EditUkmActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}