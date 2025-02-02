package com.example.pendaftaranukm.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pendaftaranukm.R;
import com.example.pendaftaranukm.api.ApiService;
import com.example.pendaftaranukm.api.RetrofitClient;
import com.example.pendaftaranukm.auth.SessionManager;
import com.example.pendaftaranukm.model.Ukm;
import com.example.pendaftaranukm.model.Leader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.pendaftaranukm.R;

public class CreateUkmActivity extends AppCompatActivity {
    private EditText etUkmName, etLeaderId, etUkmDescription;
    private EditText etLeaderNim, etLeaderName, etLeaderClass;
    private Button btnCreateLeader, btnCreateUkm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ukm);

        etLeaderNim = findViewById(R.id.etLeaderNim);
        etLeaderName = findViewById(R.id.etLeaderName);
        etLeaderClass = findViewById(R.id.etLeaderClass);

        etUkmName = findViewById(R.id.etUkmName);
        etLeaderId = findViewById(R.id.etLeaderId);
        etUkmDescription = findViewById(R.id.etUkmDescription);
        btnCreateLeader = findViewById(R.id.btnCreateLeader);
        btnCreateUkm = findViewById(R.id.btnCreateUkm);

        btnCreateLeader.setOnClickListener(v -> createLeader());
        btnCreateUkm.setOnClickListener(v -> createUkm());
    }

    private void createLeader() {
        String nim = etLeaderNim.getText().toString();
        String name = etLeaderName.getText().toString();
        String leaderClass = etLeaderClass.getText().toString();

        if (nim.isEmpty() || name.isEmpty() || leaderClass.isEmpty()) {
            Toast.makeText(this, "Semua data Ketua harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        Leader leader = new Leader();
        leader.setNim(nim);
        leader.setName(name);
        leader.setLeaderClass(leaderClass);

        // Kirim data UKM ke API
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<Leader> call = apiService.createLeader(leader);
        call.enqueue(new Callback<Leader>() {
            @Override
            public void onResponse(Call<Leader> call, Response<Leader> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateUkmActivity.this, "Leader berhasil dibuat", Toast.LENGTH_SHORT).show();
                    finish(); // Tutup activity setelah UKM berhasil dibuat
                } else {
                    Toast.makeText(CreateUkmActivity.this, "Gagal membuat Leader", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Leader> call, Throwable t) {
                Toast.makeText(CreateUkmActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createUkm() {
        String name = etUkmName.getText().toString();
        String description = etUkmDescription.getText().toString();

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Nama dan deskripsi UKM harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buat objek Leader
        Leader leader = new Leader();
        leader.setId(Long.valueOf(etLeaderId.getText().toString()));
        leader.setNim(etLeaderNim.getText().toString());
        leader.setName(etLeaderName.getText().toString());
        leader.setLeaderClass(etLeaderClass.getText().toString());

        // Buat objek UKM
        Ukm ukm = new Ukm();
        ukm.setName(name);
        ukm.setLeader(leader);
        ukm.setDescription(description);

        // Kirim data UKM ke API
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<Ukm> call = apiService.createUkm(ukm);
        call.enqueue(new Callback<Ukm>() {
            @Override
            public void onResponse(Call<Ukm> call, Response<Ukm> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateUkmActivity.this, "UKM berhasil dibuat", Toast.LENGTH_SHORT).show();
                    finish(); // Tutup activity setelah UKM berhasil dibuat
                } else {
                    Toast.makeText(CreateUkmActivity.this, "Gagal membuat UKM", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ukm> call, Throwable t) {
                Toast.makeText(CreateUkmActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}