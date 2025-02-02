//package com.example.pendaftaranukm.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.pendaftaranukm.adapter.UkmAdapter;
//import com.example.pendaftaranukm.api.ApiService;
//import com.example.pendaftaranukm.api.RetrofitClient;
//import com.example.pendaftaranukm.auth.SessionManager;
//import com.example.pendaftaranukm.model.Registration;
//import com.example.pendaftaranukm.model.Ukm;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import com.example.pendaftaranukm.R;
//import com.example.pendaftaranukm.model.User;
//
//public class UkmListActivity extends AppCompatActivity {
//    private RecyclerView recyclerView;
//    private UkmAdapter ukmAdapter;
//    private List<Ukm> ukmList = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ukm_list);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        ukmAdapter = new UkmAdapter(ukmList, position -> {
//            Ukm selectedUkm = ukmList.get(position);
//            Toast.makeText(this, "Anda memilih: " + selectedUkm.getName(), Toast.LENGTH_SHORT).show();
//
//            // Logika untuk mendaftar ke UKM
//            registerToUkm(selectedUkm);
//        });
//
//        recyclerView.setAdapter(ukmAdapter);
//
//        // Muat daftar UKM dari API
//        loadUkmList();
//    }
//
//    private void loadUkmList() {
//        ApiService apiService = RetrofitClient.getApiService(this);
//        Call<List<Ukm>> call = apiService.getAllUkm();
//        call.enqueue(new Callback<List<Ukm>>() {
//            @Override
//            public void onResponse(Call<List<Ukm>> call, Response<List<Ukm>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    ukmList.clear();
//                    ukmList.addAll(response.body());
//                    ukmAdapter.notifyDataSetChanged(); // Perbarui RecyclerView
//                } else {
//                    Toast.makeText(UkmListActivity.this, "Gagal memuat daftar UKM", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Ukm>> call, Throwable t) {
//                Toast.makeText(UkmListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    private void registerToUkm(Ukm ukm) {
////        SessionManager sessionManager = new SessionManager(this);
////        String userEmail = sessionManager.getUserEmail();
////
////        if (userEmail == null || userEmail.isEmpty()) {
////            Toast.makeText(this, "Gagal mendapatkan informasi pengguna", Toast.LENGTH_SHORT).show();
////            return;
////        }
////
////        // Buat objek Registration
////        Registration registration = new Registration();
////        User user = new User();
////        user.setEmail(userEmail);
////
////        registration.setUser(user);
////        registration.setUkm(ukm);
////        registration.setStatus("PENDING"); // Status awal pendaftaran
////        registration.setRegistrationDate(new Date()); // Tanggal pendaftaran
////
////        // Kirim data ke API
////        ApiService apiService = RetrofitClient.getApiService(this);
////        Call<Registration> call = apiService.createRegistration(registration);
////        call.enqueue(new Callback<Registration>() {
////            @Override
////            public void onResponse(Call<Registration> call, Response<Registration> response) {
////                if (response.isSuccessful()) {
////                    Toast.makeText(UkmListActivity.this, "Pendaftaran berhasil, menunggu persetujuan", Toast.LENGTH_SHORT).show();
////                } else {
////                    Toast.makeText(UkmListActivity.this, "Gagal mendaftar ke UKM", Toast.LENGTH_SHORT).show();
////                }
////            }
////
////            @Override
////            public void onFailure(Call<Registration> call, Throwable t) {
////                Toast.makeText(UkmListActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
////            }
////        });
//    }
//}