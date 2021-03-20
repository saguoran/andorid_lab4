package com.example.android_lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private final AppRepository repo ;
    private final MutableLiveData<NurseWithPatients> nurseWithPatients;
    boolean authenticated = false;
    private LiveData<List<Test>> testLiveData;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repo = new AppRepository(application);
        nurseWithPatients = repo.getNurseWithPatients();
    }


    public MutableLiveData<NurseWithPatients> getNurseWithPatients() {
        return nurseWithPatients;
    }

    public void register(Nurse nurse) {
        repo.insertNurse(nurse);
    }
    public void login(String id,String password) {
        repo.findNurseWithPatientsByNurseId(id,password);
    }
    public void update(Patient patient){
        repo.update(patient);
    }

    // below section is for Test
    public LiveData<List<Test>> getTestLiveData() {
        return repo.getAllTests();
    }

    public void newTest(Test test) {
        repo.insertTest(test);
    }
}
