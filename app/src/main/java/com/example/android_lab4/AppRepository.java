package com.example.android_lab4;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class AppRepository {

    private final AppDao appDao;
    private LiveData<Nurse> nurseLiveData;

    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private LiveData<List<Test>> testList;

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
