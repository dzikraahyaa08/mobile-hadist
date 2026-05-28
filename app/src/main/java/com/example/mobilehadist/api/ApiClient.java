package com.example.mobilehadist.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Menggunakan IP Laptop Anda agar bisa diakses dari emulator/HP asli
    // Pastikan Firewall mati agar koneksi ke 192.168.100.40 tidak ditolak
    private static final String BASE_URL = "http://192.168.100.40/mobilehadist/";
    private static Retrofit retrofit = null;

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
