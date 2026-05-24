package com.example.mobilehadist.dao;

import androidx.room.Dao;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.example.mobilehadist.model.Hadith;
import java.util.List;

@Dao
public interface HadithDao {
    // Fungsi sakti untuk mengambil data dari tabel mana saja secara dinamis
    @RawQuery
    List<Hadith> getHadithsFromTable(SupportSQLiteQuery query);

    @RawQuery
    Hadith getSingleHadith(SupportSQLiteQuery query);
}
