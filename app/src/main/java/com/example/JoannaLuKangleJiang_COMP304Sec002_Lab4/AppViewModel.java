package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private final AppRepository repo ;
    private final MutableLiveData<NurseWithPatients> nurseWithPatients;
    private final MutableLiveData<Patient> patientMutableLiveData;
    boolean authenticated = false;
    private final LiveData<List<Test>> testLiveData;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repo = new AppRepository(application);
        nurseWithPatients = repo.getNurseWithPatients();
        patientMutableLiveData = repo.getPatient();
        testLiveData = repo.getAllTests();
    }


    public MutableLiveData<NurseWithPatients> getNurseWithPatients() {
        return nurseWithPatients;
    }
    public MutableLiveData<Patient> getPatient() {
        return patientMutableLiveData;
    }
    public void getPatientById(int patientId){
        repo.getPatientById(patientId);
    }
    public void register(Nurse nurse) {
        repo.insert(nurse);
    }
    public void findNurseWithPatientsByNurseId(String nurseId) {
        repo.findNurseWithPatientsByNurseId(nurseId);
    }
    public void login(String id,String password) {
        repo.findNurseWithPatientsByNurseId(id,password);
    }
    public void update(Patient patient){
        repo.update(patient);
    }
    public void insert(Patient patient){
        repo.insert(patient);
    }

    // below section is for Test
    public LiveData<List<Test>> getAllTestLiveData() {
        return repo.getAllTests();
    }

    public void newTest(Test test) {
        repo.insertTest(test);
    }

    public LiveData<List<Test>> getAllTestByPatientIdLiveData(int patiendId) {
        return repo.getAllTestsByPatiendId(patiendId);
    }
}
