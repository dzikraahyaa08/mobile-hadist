package com.example.mobilehadist;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilehadist.model.Hadith;
import com.example.mobilehadist.repository.GameRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GameRepository gameRepository;
    private Hadith currentHadith;
    private int currentHadithNumber = 1; // Mulai dari hadits nomor 1
    private final String SELECTED_TABLE = "shahih_bukhari"; // Ganti sesuai tabel di .sqlite Anda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        gameRepository = new GameRepository(this);

        // Load level pertama
        loadLevel(currentHadithNumber);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadLevel(int nomor) {
        gameRepository.getHadithsByKitab(SELECTED_TABLE, new GameRepository.LoadCallback<List<Hadith>>() {
            @Override
            public void onLoaded(List<Hadith> hadiths) {
                if (hadiths != null && !hadiths.isEmpty()) {
                    // Cari hadits yang nomornya sesuai urutan
                    for (Hadith h : hadiths) {
                        if (h.nomor == nomor) {
                            currentHadith = h;
                            runOnUiThread(() -> {
                                Log.d("GAME_LOG", "Level Dimuat: " + h.nomor);
                                // Tim UI akan mengambil h.getShuffledWords() di sini
                            });
                            return;
                        }
                    }
                }
            }
        });
    }

    /**
     * Dipanggil saat user selesai menyusun kata (Drag & Drop selesai)
     * @param userJoinedWords kalimat yang disusun user
     */
    public void checkUserAnswer(String userJoinedWords) {
        if (currentHadith != null && currentHadith.checkAnswer(userJoinedWords)) {
            // JIKA BENAR: Tandai Lulus, Simpan Skor, Lanjut Nomor Berikutnya
            gameRepository.markAsPassed("User1", currentHadith, 100, 1);
            
            Toast.makeText(this, "LULUS HADITS NOMOR " + currentHadithNumber, Toast.LENGTH_SHORT).show();
            
            // Lanjut ke level berikutnya
            currentHadithNumber++;
            loadLevel(currentHadithNumber);
        } else {
            // JIKA SALAH: Getarkan HP atau beri notifikasi (Tidak bisa lanjut)
            Toast.makeText(this, "Salah! Susun kembali dengan benar.", Toast.LENGTH_LONG).show();
        }
    }
}
