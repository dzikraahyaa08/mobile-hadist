package com.example.mobilehadist.repository;

import android.content.Context;
import androidx.sqlite.db.SimpleSQLiteQuery;
import com.example.mobilehadist.database.AppDatabase;
import com.example.mobilehadist.model.Hadith;
import com.example.mobilehadist.model.UserProgress;
import com.example.mobilehadist.model.UserScore;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRepository {
    private final AppDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public GameRepository(Context context) {
        db = AppDatabase.getInstance(context);
    }

    // Mengambil data dari tabel dinamis (misal: shahih_bukhari)
    public void getHadithsByKitab(String tableName, LoadCallback<List<Hadith>> callback) {
        executor.execute(() -> {
            String queryString = "SELECT * FROM " + tableName;
            SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString);
            List<Hadith> hadiths = db.hadithDao().getHadithsFromTable(query);
            callback.onLoaded(hadiths);
        });
    }

    public void markAsPassed(String username, Hadith hadith, int score, int streak) {
        executor.execute(() -> {
            db.userProgressDao().saveProgress(new UserProgress(hadith.kitab, hadith.nomor, true));
            db.userScoreDao().insert(new UserScore(username, score, streak, 0, System.currentTimeMillis()));
        });
    }

    public interface LoadCallback<T> {
        void onLoaded(T data);
    }
}
