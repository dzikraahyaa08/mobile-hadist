package com.example.mobilehadist.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Model Hadith untuk Retrofit.
 * Anotasi Room dihapus untuk mendukung migrasi ke MySQL.
 */
public class Hadith {
    public int id;
    public String kitab;
    public int nomor;
    public String arab;
    public String terjemah;

    public Hadith(int id, String kitab, int nomor, String arab, String terjemah) {
        this.id = id;
        this.kitab = kitab;
        this.nomor = nomor;
        this.arab = arab;
        this.terjemah = terjemah;
    }

    public List<String> getShuffledWords() {
        List<String> words = Arrays.asList(arab.split(" "));
        Collections.shuffle(words);
        return words;
    }
}
