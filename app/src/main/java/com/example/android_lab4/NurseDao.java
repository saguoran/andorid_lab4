package com.example.android_lab4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NurseDao  {
    @Query("SELECT * FROM nurse WHERE nurseId = :nurseId AND password = :password")
    LiveData<Nurse> getByUserIdAndPassword(String nurseId, String password);
    @Query("SELECT * FROM nurse")
    List<Nurse> getAll();

    @Query("SELECT * FROM nurse WHERE nurseId IN (:nurseIds)")
    List<Nurse> loadAllByIds(String[] nurseIds);

    @Query("SELECT * FROM nurse WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    Nurse findByName(String first, String last);

    @Insert
    void insertAll(Nurse... nurses);

    @Delete
    void delete(Nurse nurse);
}
