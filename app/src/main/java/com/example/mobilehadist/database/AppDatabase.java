package com.example.mobilehadist.database;

import android.content.Context;
import com.example.mobilehadist.dao.HadithDao;
import com.example.mobilehadist.dao.UserProgressDao;
import com.example.mobilehadist.dao.UserScoreDao;

/**
 * File ini adalah Stub (kerangka) agar aplikasi tidak error setelah library Room dihapus.
 * Aplikasi sekarang beralih menggunakan Retrofit untuk mengambil data dari MySQL.
 */
public class AppDatabase {
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AppDatabase();
        }
        return INSTANCE;
    }

    // Mengembalikan null karena data sekarang dikelola via ApiService (Retrofit)
    public HadithDao hadithDao() { return null; }
    public UserScoreDao userScoreDao() { return null; }
    public UserProgressDao userProgressDao() { return null; }
}
