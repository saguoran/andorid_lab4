package com.example.android_lab4;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {

    /// nurse
    @Query("SELECT * FROM nurse WHERE nurseId = :nurseId AND password = :password")
    LiveData<Nurse> getByUserIdAndPassword(String nurseId, String password);
    @Query("SELECT * FROM nurse")
    List<Nurse> getAllNurse();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Nurse... nurses);
    @Delete
    void delete(Nurse nurse);
    /// patient
    @Transaction
    @Query("SELECT * FROM nurse Where nurseId = :nurseId AND password = :password")
    public NurseWithPatients getNurseWithPatientsByNurseId(String nurseId, String password);
    @Transaction
    @Query("SELECT * FROM nurse Where nurseId = :nurseId")
        public NurseWithPatients getNurseWithPatientsByNurseId(String nurseId);
    @Query("SELECT * FROM patient")
    List<Patient> getAll();

    @Query("SELECT * FROM patient WHERE patientId IN (:patientIds)")
    List<Patient> loadAllByIds(int[] patientIds);
    @Query("SELECT * FROM patient WHERE patientId = :patientId")
    Patient loadPatientById(int patientId);

    @Query("SELECT * FROM patient WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    Patient findByName(String first, String last);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Patient... patients);

    @Delete
    void delete(Patient patient);
    @Update
    void update(Patient... patient);

    // below section is for Test
    //insert test
    @Insert
    void insertAll(Test... test);

    //delete test
    @Delete
    void delete(Test test);

    //Monitoring Query Result Changes with Live Data
    @Query("select * from test order by testId")
    LiveData<List<Test>> getAllTests();

    // return test records from given patient id
    @Query("SELECT * FROM test WHERE patientId IN (:patientId)")
    LiveData<List<Test>> getTestByPatiendId(int patientId);
}

