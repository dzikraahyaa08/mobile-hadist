package com.example.mobilehadist.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Alamat IP Laptop Anda (XAMPP)
    // Pastikan HP/Emulator dan Laptop di satu WiFi yang sama
    // Dan Firewall Laptop sudah dimatikan
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
