package com.example.pendaftaranukm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pendaftaranukm.R;
import com.example.pendaftaranukm.activity.CreateUkmActivity;
import com.example.pendaftaranukm.activity.EditUkmActivity;
import com.example.pendaftaranukm.activity.ProfileActivity;
import com.example.pendaftaranukm.activity.RegistrationActivity;
//import com.example.pendaftaranukm.activity.UkmListActivity;
import com.example.pendaftaranukm.adapter.UkmAdapter;
import com.example.pendaftaranukm.api.ApiService;
import com.example.pendaftaranukm.api.RetrofitClient;
import com.example.pendaftaranukm.auth.SessionManager;
import com.example.pendaftaranukm.model.Registration;
import com.example.pendaftaranukm.model.Ukm;
import com.example.pendaftaranukm.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UkmAdapter ukmAdapter;
    private List<Ukm> ukmList = new ArrayList<>();
    private User userAlt;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowMenu = findViewById(R.id.btnShowMenu);
        btnShowMenu.setOnClickListener(v -> showPopupMenu(v));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ukmAdapter = new UkmAdapter(ukmList, position -> {
            Ukm selectedUkm = ukmList.get(position);
            showUkmOptionsDialog(selectedUkm);
        });

        recyclerView.setAdapter(ukmAdapter);

        getUserId();

        // Muat daftar UKM dari API
        loadUkmList();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_main); // Inflate menu_main.xml

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_create_ukm) {
                // Buka CreateUkmActivity saat tombol "Buat UKM Baru" diklik
                Intent intent = new Intent(this, CreateUkmActivity.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.menu_update_profile) {
                // Buka ProfileActivity saat tombol "Update Profile" diklik
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;
            } else if(item.getItemId() == R.id.action_reload) {
                loadUkmList();
            }
            return super.onOptionsItemSelected(item);
        });

        popupMenu.show();
    }

    private void loadUkmList() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<List<Ukm>> call = apiService.getAllUkm();
        call.enqueue(new Callback<List<Ukm>>() {
            @Override
            public void onResponse(Call<List<Ukm>> call, Response<List<Ukm>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ukmList.clear();
                    ukmList.addAll(response.body());
                    ukmAdapter.notifyDataSetChanged(); // Perbarui RecyclerView
                } else {
                    Toast.makeText(MainActivity.this, "Gagal memuat daftar UKM", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ukm>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUkmOptionsDialog(Ukm ukm) {
        String[] options = {"Mendaftar", "Edit UKM", "Lihat Pendaftar", "Hapus"};

        new android.app.AlertDialog.Builder(this)
                .setTitle("Pilihan UKM")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            registerToUkm(ukm);
                            break;
                        case 1:
                            editUkm(ukm);
                            break;
                        case 2:
                            viewRegistrations(ukm);
                            break;
                        case 3:
                            deleteUkm(ukm);
                            break;
                    }
                })
                .show();
    }

    private void getUserId() {
        ApiService apiServiceEmail = RetrofitClient.getApiService(this);
        Call<User> callEmail = apiServiceEmail.getProfile(); // API untuk mendapatkan profil pengguna
        callEmail.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userAlt = response.body();
                    userId = userAlt.getId();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> callEmail, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void registerToUkm(Ukm ukm) {
        if (userId == 0) {
            Toast.makeText(this, "User ID tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService(this);
        Call<List<Ukm>> call = apiService.getUkmByName(ukm.getName());
        call.enqueue(new Callback<List<Ukm>>() {
            @Override
            public void onResponse(Call<List<Ukm>> call, Response<List<Ukm>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    // Ambil UKM pertama dari daftar
                    Ukm firstUkm = response.body().get(0);
                    sendRegistration(firstUkm);
                } else {
                    Toast.makeText(MainActivity.this, "Gagal mendapatkan ID UKM", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ukm>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendRegistration(Ukm ukmAlt) {
        User user = new User();
        user.setId(userAlt.getId());
        user.setNim(userAlt.getNim());
        user.setEmail(userAlt.getEmail());
        user.setName(userAlt.getName());

        Ukm ukm = new Ukm();
        ukm.setId(ukmAlt.getId());
        ukm.setName(ukmAlt.getName());

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setUkm(ukm);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(new Date());
        registration.setRegistrationDate(formattedDate);
        Toast.makeText(MainActivity.this, "Tanggal: " + registration.getRegistrationDate(), Toast.LENGTH_SHORT).show();
        registration.setStatus("PENDING");

        ApiService apiService = RetrofitClient.getApiService(this);
        Call<Registration> call = apiService.createRegistration(registration);
        call.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Pendaftaran berhasil, menunggu persetujuan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Gagal mendaftar ke UKM", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void editUkm(Ukm ukm) {
        // Buka EditUkmActivity dengan membawa data UKM
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<List<Ukm>> call = apiService.getUkmByName(ukm.getName());
        call.enqueue(new Callback<List<Ukm>>() {
            @Override
            public void onResponse(Call<List<Ukm>> call, Response<List<Ukm>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    // Ambil UKM pertama dari daftar
                    Ukm firstUkm = response.body().get(0);
                    Intent intent = new Intent(MainActivity.this, EditUkmActivity.class);
                    intent.putExtra("UKM_ID", firstUkm.getId());
                    intent.putExtra("UKM_NAME", firstUkm.getName());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Gagal mendapatkan ID UKM", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ukm>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewRegistrations(Ukm ukm) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra("UKM_ID", ukm.getId());
        intent.putExtra("UKM_NAME", ukm.getName());
        startActivity(intent);
    }

    private void deleteUkm(Ukm ukm) {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<Void> call = apiService.deleteUkm(ukm.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "UKM berhasil dihapus", Toast.LENGTH_SHORT).show();
                    loadUkmList(); // Muat ulang daftar UKM
                } else {
                    Toast.makeText(MainActivity.this, "Gagal menghapus UKM", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}