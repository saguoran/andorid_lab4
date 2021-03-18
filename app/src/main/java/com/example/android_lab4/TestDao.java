package com.example.android_lab4;


import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Dao;

import java.util.List;

// this interface declares database functions
// and does the mapping of SQL queries to functions
@Dao
public interface TestDao {

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


