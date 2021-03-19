package com.example.android_lab4;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

public class AppRepository {

    private final NurseDao nurseDao;
    private final PatientDao patientDao;
    private LiveData<Nurse> nurseLiveData;

    AppRepository(Application application) {
//        AppDatabase db = Room.databaseBuilder(application,
//                AppDatabase.class, "lab4_db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        AppDatabase db = AppDatabase.getDatabase(application);
        new Thread(db::clearAllTables).start();
        nurseDao = db.nurseDao();
        patientDao = db.patientDao();
    }

    public LiveData<Nurse> getNurseLiveData() {
        return nurseLiveData;
    }

    public void insertNurse(Nurse nurse) {
        new Thread(() -> {
            nurseDao.insertAll(nurse);
            nurseLiveData = nurseDao.getByUserIdAndPassword(nurse.nurseId, nurse.password);
        }).start();
    }
    public void insertPatient(Patient p){
        new Thread(() -> {
        patientDao.insertAll(p);}).start();
    }

    public void findNurseByIdAndPassword(String nurseId, String password) {
//        new Thread(() -> {
            nurseLiveData = nurseDao.getByUserIdAndPassword(nurseId, password);
//        }).start();
    }

    public LiveData<NurseWithPatients> findNurseWithPatientsByNurseId(String nurseId){
        return nurseDao.getNurseWithPatientsByNurseId(nurseId);
    }


}
