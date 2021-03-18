package com.example.android_lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TestViewModel extends AndroidViewModel {

    private TestRepository repo;
    private LiveData<List<Test>> testLiveData;

    public TestViewModel(@NonNull Application application) {
        super(application);
        repo = new TestRepository(application);
    }

    public LiveData<List<Test>> getTestLiveData() {
        return repo.getAllTests();
    }

    public void newTest(Test test) {
        repo.insert(test);
    }

}
