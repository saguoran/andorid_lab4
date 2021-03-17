package com.example.android_lab4;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public class NurseRepository {

    private final NurseDao nurseDao;
    private LiveData<Nurse> nurseLiveData;

    NurseRepository(Application application) {
        AppDatabase db = Room.databaseBuilder(application,
                AppDatabase.class, "database-name").build();
        nurseDao = db.nurseDao();
    }

    public LiveData<Nurse> getNurseLiveData() {
        return nurseLiveData;
    }

    public void insert(Nurse nurse) {
        new Thread(() -> {
            nurseDao.insertAll(nurse);
            nurseLiveData = nurseDao.getByUserIdAndPassword(nurse.nurseId, nurse.password);
        }).start();
    }

    public void findByIdAndPassword(String nurseId,String password) {
//        new Thread(() -> {
            nurseLiveData = nurseDao.getByUserIdAndPassword(nurseId, password);
//        }).start();
    }


}
