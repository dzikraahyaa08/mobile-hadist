package com.example.mobilehadist.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

@Entity(tableName = "hadith_game") // Kita gunakan satu tabel virtual/temp untuk game
public class Hadith {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String kitab;
    public int nomor;
    public String arab;
    public String terjemahan;

    public Hadith(String kitab, int nomor, String arab, String terjemahan) {
        this.kitab = kitab;
        this.nomor = nomor;
        this.arab = arab;
        this.terjemahan = terjemahan;
    }

    // Fungsi untuk memecah hadits menjadi potongan kata untuk Drag & Drop
    public List<String> getShuffledWords() {
        String[] words = arab.split("\\s+");
        List<String> wordList = Arrays.asList(words);
        Collections.shuffle(wordList); // Acak urutannya untuk tantangan
        return wordList;
    }

    // Fungsi untuk memverifikasi apakah jawaban user benar
    public boolean checkAnswer(String userJoinedWords) {
        return this.arab.trim().equals(userJoinedWords.trim());
    }
}
