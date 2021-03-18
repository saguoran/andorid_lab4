package com.example.android_lab4;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

public class TestRepository {

    private final TestDao testDao;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private LiveData<List<Test>> testList;

    // Test Repository Constructor
    public TestRepository(Application application) {
        AppDatabase db = Room.databaseBuilder(application,
                AppDatabase.class, "database-name").build();
        testDao = db.testDao();

        testList = testDao.getAllTests();
    }

    // returns query results as LiveData object
    LiveData<List<Test>> getAllTests() {
        return testList;
    }

    LiveData<List<Test>> getAllTestsByNurseIds(String nurseId) {
        return testDao.getTestByNurseIds(nurseId);
    }

    //inserts a person asynchronously
    public void insert(Test test) {
        insertAsync(test);
    }

    // returns insert results as LiveData object
    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    private void insertAsync(final Test test) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testDao.insert(test);
                    insertResult.postValue(1);
                } catch (Exception e) {
                    insertResult.postValue(0);
                }
            }
        }).start();
    }
}
