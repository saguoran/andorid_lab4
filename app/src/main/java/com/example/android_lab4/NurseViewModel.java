package com.example.android_lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NurseViewModel  extends AndroidViewModel {
    private NurseRepository repo;
    private LiveData<Nurse> nurseLiveData;
    public NurseViewModel(@NonNull Application application) {
        super(application);
        repo = new NurseRepository(application);
    }

    public LiveData<Nurse> getNurseLiveData() {
        return repo.getNurseLiveData();
    }

    public void register(Nurse nurse) {
        repo.insert(nurse);
    }
    public void login(String id,String password) {
        repo.findByIdAndPassword(id,password);
    }
}
