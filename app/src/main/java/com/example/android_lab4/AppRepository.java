package com.example.android_lab4;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AppRepository {

    private final AppDao appDao;
    private MutableLiveData<NurseWithPatients> nurseWithPatients;

    AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
//        new Thread(db::clearAllTables).start();
        appDao = db.nurseDao();
    }

    public MutableLiveData<NurseWithPatients> getNurseWithPatients() {
        if (nurseWithPatients == null) {
            nurseWithPatients = new MutableLiveData<>();
        }
        return nurseWithPatients;
    }

    public void insertNurse(Nurse nurse) {
        new Thread(() -> {
            appDao.insertAll(nurse);
        }).start();
    }


    public void findNurseWithPatientsByNurseId(String nurseId, String password) {
        new Thread(() -> {
            NurseWithPatients n =appDao.getNurseWithPatientsByNurseId(nurseId, password);
            getNurseWithPatients().postValue(n);
        }).start();
    }
    public void update(Patient patient) {
        new Thread(() -> {
            appDao.update(patient);
            NurseWithPatients n =appDao.getNurseWithPatientsByNurseId(patient.nurseId);
            getNurseWithPatients().postValue(n);
        }).start();
    }


}
