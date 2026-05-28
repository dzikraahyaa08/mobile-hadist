package com.example.mobilehadist.model;

/**
 * Model untuk progress user.
 * Sekarang berupa POJO (Plain Old Java Object) tanpa dependensi Room.
 */
public class UserProgress {
    public int id;
    public String kitab;
    public int nomor;
    public boolean isPassed;

    // Constructor kosong wajib untuk GSON / Retrofit
    public UserProgress() {
    }

    public UserProgress(String kitab, int nomor, boolean isPassed) {
        this.kitab = kitab;
        this.nomor = nomor;
        this.isPassed = isPassed;
    }
}
