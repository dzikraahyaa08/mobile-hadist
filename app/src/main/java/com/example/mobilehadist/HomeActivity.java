package com.example.mobilehadist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MaterialButton btnPlay = findViewById(R.id.btn_play);
        MaterialButton btnLeaderboard = findViewById(R.id.btn_leaderboard);

        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnLeaderboard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });
    }
}
