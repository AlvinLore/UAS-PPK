package com.example.pendaftaranukm.api;

import android.content.Context;

import com.example.pendaftaranukm.auth.SessionManager;

import android.content.Context;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.1.100:8080/";
    private static Retrofit retrofit = null;
    public static ApiService getApiService(Context context) {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                String token = new SessionManager(context).getToken();
                if (token != null) {
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "Bearer " + token);
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
                return chain.proceed(original);
            });

            Gson gson = new GsonBuilder()
                    .setLenient()  // Untuk menangani JSON yang tidak valid
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
