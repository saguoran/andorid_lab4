package com.example.android_lab4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PatientDao  {
//    @Query("SELECT * FROM patient WHERE nurseId = :nurseId")
//    List<Patient> getPatientsByNurseId(String nurseId);
    @Query("SELECT * FROM patient")
    List<Patient> getAll();

    @Query("SELECT * FROM patient WHERE patientId IN (:patientIds)")
    List<Patient> loadAllByIds(String[] patientIds);

    @Query("SELECT * FROM patient WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    Patient findByName(String first, String last);

    @Insert
    void insertAll(Patient... patients);

    @Delete
    void delete(Patient patient);
}
