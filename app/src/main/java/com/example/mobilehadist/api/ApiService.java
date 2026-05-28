package com.example.mobilehadist.api;

import com.example.mobilehadist.model.Hadith;
import com.example.mobilehadist.model.UserScore;
import com.example.mobilehadist.model.AuthResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    
    // Auth - Sinkron dengan layar "Daftar Akun Baru"
    @FormUrlEncoded
    @POST("register.php")
    Call<AuthResponse> register(
        @Field("username") String username, 
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<AuthResponse> login(
        @Field("username") String username, 
        @Field("password") String password
    );

    // Sesuai dengan nama file di htdocs Anda: get_hadits.php
    @GET("get_hadits.php")
    Call<List<Hadith>> getHadiths(@Query("kitab") String kitab);

    @POST("save_score.php")
    Call<Void> saveScore(@Body UserScore userScore);
    
    // Leaderboard
    @GET("get_leaderboard.php")
    Call<List<UserScore>> getLeaderboard();
}
