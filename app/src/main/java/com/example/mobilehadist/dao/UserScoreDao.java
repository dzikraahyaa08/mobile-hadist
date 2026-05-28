package com.example.mobilehadist.dao;

import com.example.mobilehadist.model.UserScore;
import java.util.List;

/**
 * Interface ini dinonaktifkan karena beralih ke API MySQL.
 */
public interface UserScoreDao {
    // Metode stub agar tidak terjadi error kompilasi
    List<UserScore> getTopScores();
    void insert(UserScore userScore);
    void update(UserScore userScore);
}
