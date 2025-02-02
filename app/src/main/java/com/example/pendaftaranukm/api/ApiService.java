package com.example.pendaftaranukm.api;

import com.example.pendaftaranukm.auth.AuthRequest;
import com.example.pendaftaranukm.auth.AuthResponse;
import com.example.pendaftaranukm.auth.ChangePasswordRequest;
import com.example.pendaftaranukm.model.Leader;
import com.example.pendaftaranukm.model.Registration;
import com.example.pendaftaranukm.model.Ukm;
import com.example.pendaftaranukm.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/register")
    Call<User> registerUser(@Body User user);

    @POST("/login")
    Call<AuthResponse> loginUser(@Body AuthRequest authRequest);

    @GET("/profile")
    Call<User> getProfile();

    @PUT("/profile")
    Call<User> updateProfile(@Body User user);

    @PUT("/change-password")
    Call<Void> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @POST("/registration")
    Call<Registration> createRegistration(@Body Registration registration);

    @GET("/leader")
    Call<List<Leader>> getAllLeaders();

    @GET("/leader/{id}")
    Call<Leader> getLeaderById(@Path("id") long id);

    @GET("leader/nim/{nim}")
    Call<List<Leader>> getLeaderByNim(@Path("nim") String nim);

    @POST("/leader")
    Call<Leader> createLeader(@Body Leader leader);

    @PUT("/leader/{id}")
    Call<Leader> updateLeader(@Path("id") long id, @Body Leader leader);

    @DELETE("/leader/{id}")
    Call<Void> deleteLeader(@Path("id") long id);

    @POST("/ukm")
    Call<Ukm> createUkm(@Body Ukm ukm);

    @PUT("/ukm/{id}")
    Call<Ukm> updateUkm(@Path("id") long id, @Body Ukm ukm);

    @DELETE("/ukm/{id}")
    Call<Void> deleteUkm(@Path("id") long id);

    @GET("/ukm")
    Call<List<Ukm>> getAllUkm();

    @GET("/ukm/{id}")
    Call<Ukm> getUkmById(@Path("id") long id);

    @GET("/registration/search/ukm")
    Call<List<Registration>> getRegistrationsByUkmName(@Query("name") String name);

//    @PUT("/registration/{id}")
//    Call<Void> updateRegistrationStatus(@Path("id") long id, @Query("status") String status);

    @PUT("/registration/{id}")
    public Call<Registration> updateRegistration(@Path("id") long id, @Body Registration registration);

    @GET("/user/email/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

    @GET("/ukm/search")
    Call<List<Ukm>> getUkmByName(@Query("name") String name);
}
