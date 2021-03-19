package com.example.android_lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NurseViewModel  extends AndroidViewModel {
    private AppRepository repo;
    private LiveData<NurseWithPatients> nurseWithPatients;
    public NurseViewModel(@NonNull Application application) {
        super(application);
        repo = new AppRepository(application);

    }

    public LiveData<NurseWithPatients> getNurseWithPatients() {
        return nurseWithPatients;
    }

    public void register(Nurse nurse) {
        repo.insertNurse(nurse);
    }
    public void login(String id,String password) {
        repo.findNurseByIdAndPassword(id,password);
        nurseWithPatients = repo.findNurseWithPatientsByNurseId(id);
    }
}
