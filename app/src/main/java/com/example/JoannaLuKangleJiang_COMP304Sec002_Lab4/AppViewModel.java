package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class AppViewModel extends AndroidViewModel {

    // create variables
    private final AppRepository repo;
    // constructor for AppViewModel
    public AppViewModel(@NonNull Application application) {
        super(application);
        repo = new AppRepository(application);
    }
    public AppRepository getRepo(){
        return repo;
    }

}
