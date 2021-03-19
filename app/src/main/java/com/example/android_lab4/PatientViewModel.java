package com.example.android_lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PatientViewModel extends AndroidViewModel {
    private AppRepository repo;
    public PatientViewModel(@NonNull Application application) {
        super(application);
        repo = new AppRepository(application);
    }
    public void insert(Patient p){
        repo.insertPatient(p);
    }
}
