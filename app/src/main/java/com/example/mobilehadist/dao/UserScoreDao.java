package com.example.mobilehadist.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.mobilehadist.model.UserScore;
import java.util.List;

@Dao
public interface UserScoreDao {
    @Query("SELECT * FROM leaderboard ORDER BY score DESC LIMIT 10")
    List<UserScore> getTopScores();

    @Insert
    void insert(UserScore userScore);

    @Update
    void update(UserScore userScore);
}
