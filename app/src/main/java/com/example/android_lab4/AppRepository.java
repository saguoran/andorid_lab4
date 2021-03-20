package com.example.android_lab4;

import android.app.Application;

import androidx.lifecycle.LiveData;

public class AppRepository {

    private final AppDao appDao;
    private LiveData<Nurse> nurseLiveData;

    AppRepository(Application application) {
//        AppDatabase db = Room.databaseBuilder(application,
//                AppDatabase.class, "lab4_db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        AppDatabase db = AppDatabase.getDatabase(application);
        new Thread(db::clearAllTables).start();
        appDao = db.nurseDao();
    }
    public LiveData<Nurse> getNurseLiveData() {
        return nurseLiveData;
    }

    public void insertNurse(Nurse nurse) {
        new Thread(() -> {
            appDao.insertAll(nurse);
            nurseLiveData = appDao.getByUserIdAndPassword(nurse.nurseId, nurse.password);
        }).start();
    }
    public void insertPatient(Patient p){
        new Thread(() -> {
        appDao.insertAll(p);}).start();
    }

    public void findNurseByIdAndPassword(String nurseId, String password) {
//        new Thread(() -> {
            nurseLiveData = appDao.getByUserIdAndPassword(nurseId, password);
//        }).start();
    }

    public LiveData<NurseWithPatients> findNurseWithPatientsByNurseId(String nurseId){
        return appDao.getNurseWithPatientsByNurseId(nurseId);
    }


}
