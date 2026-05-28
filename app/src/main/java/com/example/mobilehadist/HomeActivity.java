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

        // Pastikan ID ini ada di activity_home.xml
        MaterialButton btnPlay = findViewById(R.id.btn_play);
        
        if (btnPlay != null) {
            btnPlay.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            });
        }
    }
}
