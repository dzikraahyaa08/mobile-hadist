package com.example.mobilehadist.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_progress")
public class UserProgress {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String kitab;
    public int nomor;
    public boolean isPassed;

    public UserProgress(String kitab, int nomor, boolean isPassed) {
        this.kitab = kitab;
        this.nomor = nomor;
        this.isPassed = isPassed;
    }
}
