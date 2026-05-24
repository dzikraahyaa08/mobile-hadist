package com.example.mobilehadist.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "leaderboard")
public class UserScore {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private int score;
    private int streak;
    private int maxCombo;
    private long timestamp;

    public UserScore(String username, int score, int streak, int maxCombo, long timestamp) {
        this.username = username;
        this.score = score;
        this.streak = streak;
        this.maxCombo = maxCombo;
        this.timestamp = timestamp;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getStreak() { return streak; }
    public void setStreak(int streak) { this.streak = streak; }
    public int getMaxCombo() { return maxCombo; }
    public void setMaxCombo(int maxCombo) { this.maxCombo = maxCombo; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
