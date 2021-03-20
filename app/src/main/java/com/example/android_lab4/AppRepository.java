package com.example.android_lab4;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class AppRepository {

    private final AppDao appDao;
    private MutableLiveData<NurseWithPatients> nurseWithPatients;

    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private LiveData<List<Test>> testList;

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

    // below section is for Test
    // returns query results as LiveData object
    LiveData<List<Test>> getAllTests() {
        return testList;
    }

    LiveData<List<Test>> getAllTestsByPatiendId(String patientId) {
        return appDao.getTestByPatiendId(patientId);
    }

    //inserts a person asynchronously
    public void insertTest(Test test) {
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
                    appDao.insert(test);
                    insertResult.postValue(1);
                } catch (Exception e) {
                    insertResult.postValue(0);
                }
            }
        }).start();
    }
}
