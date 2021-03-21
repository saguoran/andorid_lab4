package com.example.android_lab4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

public class PatientActivity extends AppCompatActivity {
    private static final String TAG = "PatientActivity";
    public static final String PATIENT_ID = "EditPatient";
    public static final int EDIT_PATIENT = 4;
    AppViewModel viewModel;
    Patient patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        TextView firstName = findViewById(R.id.full_name);
        TextView department = findViewById(R.id.department);
        TextView room = findViewById(R.id.room);
        Intent intent = getIntent();
        int patientId = intent.getIntExtra(MainActivity.VIEW_PATIENT, -1);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AppViewModel.class);
        viewModel.getPatientById(patientId);
        viewModel.getPatient().observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(Patient patient) {
                if(patient!=null){
                    PatientActivity.this.patient = patient;
                    firstName.setText(("Full Name: "+patient.getDisplayName()));
                    department.setText(("Department: "+patient.department));
                    room.setText(("Room: "+patient.room));
                }
            }
        });
        findViewById(R.id.edit_patient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientActivity.this, EditPatientActivity.class);
                intent.putExtra(PATIENT_ID, patient.patientId);
                startActivityForResult(intent, EDIT_PATIENT );
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == EDIT_PATIENT) {
                int patientId = data.getIntExtra(PATIENT_ID, -1);
                viewModel.getPatientById(patientId);
            }
        }
    }
}