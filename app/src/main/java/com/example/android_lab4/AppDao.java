package com.example.android_lab4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface AppDao {
    /// nurse
    @Query("SELECT * FROM nurse WHERE nurseId = :nurseId AND password = :password")
    LiveData<Nurse> getByUserIdAndPassword(String nurseId, String password);
    @Query("SELECT * FROM nurse")
    List<Nurse> getAllNurse();


    @Insert
    void insertAll(Nurse... nurses);

    @Delete
    void delete(Nurse nurse);
    /// patient
    @Transaction
    @Query("SELECT * FROM nurse Where nurseId = :nurseId ")
    public LiveData<NurseWithPatients> getNurseWithPatientsByNurseId(String nurseId);
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

    // below section is for Test
    //insert test
    @Insert
    void insert(Test test);

    //delete test
    @Delete
    void delete(Test test);

    //Monitoring Query Result Changes with Live Data
    @Query("select * from Test order by testId")
    LiveData<List<Test>> getAllTests();

    // return test records from given nurse ids
    @Query("SELECT * FROM Test WHERE nurseId IN (:nurseId)")
    LiveData<List<Test>> getTestByNurseIds(String nurseId);
}

