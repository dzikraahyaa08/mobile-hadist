package com.example.mobilehadist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilehadist.model.Hadith;
import com.example.mobilehadist.repository.GameRepository;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GameRepository gameRepository;
    private Hadith currentHadith;
    private int currentHadithNumber = 1;
    private final String SELECTED_TABLE = "shahih_bukhari";

    // UI Components
    private TextView tvLevel, tvScore, tvStreak;
    private Flow flowDropZone, flowOptionsZone;
    private ConstraintLayout dropZoneContainer, optionsZoneContainer;
    private MaterialButton btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Inisialisasi UI
        initViews();

        gameRepository = new GameRepository(this);
        loadLevel(currentHadithNumber);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Event Klik Tombol Periksa
        btnCheck.setOnClickListener(v -> {
            // Logika pengecekan akan disempurnakan oleh Anggota 2 (Drag & Drop)
            // Untuk sementara kita panggil dialog sukses sebagai simulasi UI Designer
            checkUserAnswer(""); 
        });
    }

    private void initViews() {
        tvLevel = findViewById(R.id.tv_level);
        tvScore = findViewById(R.id.tv_score);
        tvStreak = findViewById(R.id.tv_streak);
        flowDropZone = findViewById(R.id.flow_drop_zone);
        flowOptionsZone = findViewById(R.id.flow_options_zone);
        dropZoneContainer = findViewById(R.id.drop_zone_container);
        optionsZoneContainer = findViewById(R.id.options_zone_container);
        btnCheck = findViewById(R.id.btn_check);
    }

    private void loadLevel(int nomor) {
        gameRepository.getHadithsByKitab(SELECTED_TABLE, new GameRepository.LoadCallback<List<Hadith>>() {
            @Override
            public void onLoaded(List<Hadith> hadiths) {
                if (hadiths != null && !hadiths.isEmpty()) {
                    for (Hadith h : hadiths) {
                        if (h.nomor == nomor) {
                            currentHadith = h;
                            runOnUiThread(() -> {
                                updateUIHeader(h.nomor);
                                displayHadithWords(h.getShuffledWords());
                            });
                            return;
                        }
                    }
                }
            }
        });
    }

    private void updateUIHeader(int level) {
        tvLevel.setText(getString(R.string.level_label, level));
        tvScore.setText(getString(R.string.score_label, 0));
        tvStreak.setText(getString(R.string.streak_label, 0));
    }

    private void displayHadithWords(List<String> words) {
        // Bersihkan zona sebelum memuat yang baru
        clearZone(optionsZoneContainer, flowOptionsZone);
        clearZone(dropZoneContainer, flowDropZone);

        LayoutInflater inflater = LayoutInflater.from(this);
        
        for (String word : words) {
            // Gunakan parent (optionsZoneContainer) saat inflate agar LayoutParams benar
            View wordCard = inflater.inflate(R.layout.item_word, optionsZoneContainer, false);
            wordCard.setId(View.generateViewId());
            
            TextView tvWord = wordCard.findViewById(R.id.tv_word_text);
            tvWord.setText(word);

            // Tambahkan View ke Container
            optionsZoneContainer.addView(wordCard);
            
            // Tambahkan ID ke Flow
            int[] referencedIds = flowOptionsZone.getReferencedIds();
            int[] newIds = new int[referencedIds.length + 1];
            System.arraycopy(referencedIds, 0, newIds, 0, referencedIds.length);
            newIds[referencedIds.length] = wordCard.getId();
            flowOptionsZone.setReferencedIds(newIds);
        }
    }

    private void clearZone(ViewGroup container, Flow flow) {
        if (container == null || flow == null) return;
        
        // Hapus semua view anak kecuali Flow itu sendiri
        for (int i = container.getChildCount() - 1; i >= 0; i--) {
            View child = container.getChildAt(i);
            if (child.getId() != flow.getId()) {
                container.removeViewAt(i);
            }
        }
        // Reset referensi ID pada Flow
        flow.setReferencedIds(new int[0]);
    }

    public void showSuccessDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_success, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        dialogView.findViewById(R.id.btn_dialog_next).setOnClickListener(v -> {
            dialog.dismiss();
            currentHadithNumber++;
            loadLevel(currentHadithNumber);
        });

        dialog.show();
    }

    public void checkUserAnswer(String userJoinedWords) {
        // Simulasi berhasil untuk keperluan testing UI
        if (currentHadith != null) {
            showSuccessDialog();
        }
    }
}
