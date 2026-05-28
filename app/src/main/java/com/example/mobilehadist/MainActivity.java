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

    // Score & Streak
    private int totalScore = 0;
    private int currentStreak = 0;

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

        initViews();

        gameRepository = new GameRepository(this);
        loadLevel(currentHadithNumber);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCheck.setOnClickListener(v -> checkUserAnswer());
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
                    boolean found = false;
                    for (Hadith h : hadiths) {
                        if (h.nomor == nomor) {
                            currentHadith = h;
                            runOnUiThread(() -> {
                                updateUIHeader(h.nomor);
                                displayHadithWords(h.getShuffledWords());
                            });
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Level " + nomor + " belum tersedia.", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Gagal koneksi ke server.", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void updateUIHeader(int level) {
        tvLevel.setText(getString(R.string.level_label, level));
        tvScore.setText(getString(R.string.score_label, totalScore));
        tvStreak.setText(getString(R.string.streak_label, currentStreak));
    }

    private void displayHadithWords(List<String> words) {
        clearZone(optionsZoneContainer, flowOptionsZone);
        clearZone(dropZoneContainer, flowDropZone);

        for (String word : words) {
            addWordToOptions(word);
        }
    }

    private void addWordToOptions(String word) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View wordCard = inflater.inflate(R.layout.item_word, optionsZoneContainer, false);
        wordCard.setId(View.generateViewId());

        TextView tvWord = wordCard.findViewById(R.id.tv_word_text);
        tvWord.setText(word);

        wordCard.setOnClickListener(v -> {
            optionsZoneContainer.removeView(wordCard);
            removeFromFlow(flowOptionsZone, wordCard.getId());
            addToDropZone(wordCard);
        });

        optionsZoneContainer.addView(wordCard);
        addToFlow(flowOptionsZone, wordCard.getId());
    }

    private void addToDropZone(View wordCard) {
        wordCard.setOnClickListener(v -> {
            dropZoneContainer.removeView(wordCard);
            removeFromFlow(flowDropZone, wordCard.getId());
            addToOptionsZone(wordCard);
        });

        dropZoneContainer.addView(wordCard);
        addToFlow(flowDropZone, wordCard.getId());
    }

    private void addToOptionsZone(View wordCard) {
        wordCard.setOnClickListener(v -> {
            optionsZoneContainer.removeView(wordCard);
            removeFromFlow(flowOptionsZone, wordCard.getId());
            addToDropZone(wordCard);
        });

        optionsZoneContainer.addView(wordCard);
        addToFlow(flowOptionsZone, wordCard.getId());
    }

    private void addToFlow(Flow flow, int viewId) {
        int[] referencedIds = flow.getReferencedIds();
        int[] newIds = new int[referencedIds.length + 1];
        System.arraycopy(referencedIds, 0, newIds, 0, referencedIds.length);
        newIds[referencedIds.length] = viewId;
        flow.setReferencedIds(newIds);
    }

    private void removeFromFlow(Flow flow, int viewId) {
        int[] referencedIds = flow.getReferencedIds();
        if (referencedIds.length == 0) return;
        int[] newIds = new int[referencedIds.length - 1];
        int count = 0;
        for (int id : referencedIds) {
            if (id != viewId) {
                newIds[count++] = id;
            }
        }
        flow.setReferencedIds(newIds);
    }

    private void clearZone(ViewGroup container, Flow flow) {
        if (container == null || flow == null) return;
        for (int i = container.getChildCount() - 1; i >= 0; i--) {
            View child = container.getChildAt(i);
            if (child.getId() != flow.getId()) {
                container.removeViewAt(i);
            }
        }
        flow.setReferencedIds(new int[0]);
    }

    private void checkUserAnswer() {
        if (currentHadith == null) return;

        StringBuilder userJoinedWords = new StringBuilder();
        int[] dropZoneIds = flowDropZone.getReferencedIds();
        
        for (int i = 0; i < dropZoneIds.length; i++) {
            View v = findViewById(dropZoneIds[i]);
            if (v != null) {
                TextView tv = v.findViewById(R.id.tv_word_text);
                userJoinedWords.append(tv.getText().toString());
                if (i < dropZoneIds.length - 1) {
                    userJoinedWords.append(" ");
                }
            }
        }

        if (userJoinedWords.toString().trim().equals(currentHadith.arab.trim())) {
            // Benar
            totalScore += 100 + (currentStreak * 10);
            currentStreak++;
            showSuccessDialog();
        } else {
            // Salah
            currentStreak = 0;
            updateUIHeader(currentHadith.nomor);
            Toast.makeText(this, "Susunan masih salah. Coba lagi!", Toast.LENGTH_SHORT).show();
        }
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
}
