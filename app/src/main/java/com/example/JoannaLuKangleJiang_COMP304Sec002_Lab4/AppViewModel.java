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
    private final MutableLiveData<NurseWithPatients> nurseWithPatients;
    private final MutableLiveData<Patient> patientMutableLiveData;
    boolean authenticated = false;
    private final LiveData<List<Test>> testLiveData;

    // constructor for AppViewModel
    public AppViewModel(@NonNull Application application) {
        super(application);
        repo = new AppRepository(application);
        nurseWithPatients = repo.getNurseWithPatients();
        patientMutableLiveData = repo.getPatient();
        testLiveData = repo.getAllTests();
    }

    // return nurseWithPatients
    public MutableLiveData<NurseWithPatients> getNurseWithPatients() {
        return nurseWithPatients;
    }

    // return patientMutableLiveData
    public MutableLiveData<Patient> getPatient() {
        return patientMutableLiveData;
    }

    // load patient by patient id
    public void getPatientById(int patientId) {
        repo.getPatientById(patientId);
    }

    // register (insert) nurse
    public void register(Nurse nurse) {
        repo.insert(nurse);
    }

    // load findNurseWithPatientsByNurseId
    public void findNurseWithPatientsByNurseId(String nurseId) {
        repo.findNurseWithPatientsByNurseId(nurseId);
    }

    // login with id and password
    public void login(String id, String password) {
        repo.findNurseWithPatientsByNurseId(id, password);
    }

    // update patient
    public void update(Patient patient) {
        repo.update(patient);
    }

    // insert patient
    public void insert(Patient patient) {
        repo.insert(patient);
    }

    // below section is for Test
    public LiveData<List<Test>> getAllTestLiveData() {
        return repo.getAllTests();
    }

    // insert test
    public void newTest(Test test) {
        repo.insertTest(test);
    }

    // return tests by patient id
    public LiveData<List<Test>> getAllTestByPatientIdLiveData(int patiendId) {
        return repo.getAllTestsByPatiendId(patiendId);
    }
}
