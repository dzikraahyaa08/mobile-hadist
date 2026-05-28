package com.example.mobilehadist.repository;

import android.content.Context;
import android.util.Log;
import com.example.mobilehadist.api.ApiClient;
import com.example.mobilehadist.api.ApiService;
import com.example.mobilehadist.model.Hadith;
import com.example.mobilehadist.model.UserScore;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameRepository {
    private final ApiService apiService;

    public GameRepository(Context context) {
        apiService = ApiClient.getApiService();
    }

    // Ambil data dari XAMPP
    public void getHadithsByKitab(String kitab, LoadCallback<List<Hadith>> callback) {
        apiService.getHadiths(kitab).enqueue(new Callback<List<Hadith>>() {
            @Override
            public void onResponse(Call<List<Hadith>> call, Response<List<Hadith>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onLoaded(response.body(), null);
                } else {
                    callback.onLoaded(null, "Server Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Hadith>> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
                callback.onLoaded(null, t.getMessage());
            }
        });
    }

    public void markAsPassed(String username, Hadith hadith, int score, int streak) {
        UserScore userScore = new UserScore(username, score, streak, 0, System.currentTimeMillis());
        apiService.saveScore(userScore).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("API_SUCCESS", "Skor tersimpan di XAMPP!");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "Gagal simpan skor");
            }
        });
    }

    public interface LoadCallback<T> {
        void onLoaded(T data, String error);
    }
}
