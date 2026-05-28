package com.example.mobilehadist.dao;

import com.example.mobilehadist.model.Hadith;
import java.util.List;

/**
 * Interface ini dinonaktifkan karena proyek beralih ke API MySQL.
 * Dibiarkan sebagai stub agar tidak menyebabkan error kompilasi pada AppDatabase.
 */
public interface HadithDao {
    // Metode stub untuk mencegah error pada kode lama
    List<Hadith> getHadithsFromTable(Object query);
    Hadith getSingleHadith(Object query);
}
