package com.example.android_lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditPatientActivity extends AppCompatActivity {
    private static final String TAG = "EditPatientActivity";
    public Patient patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);
        EditText firstName = findViewById(R.id.first_name);
        EditText lastName = findViewById(R.id.last_name);
        EditText department = findViewById(R.id.department);
        EditText room = findViewById(R.id.room);
        Intent intent = getIntent();
        int patientId = intent.getIntExtra(PatientActivity.PATIENT_ID, -1);
        AppViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AppViewModel.class);
        viewModel.getPatientById(patientId);
        viewModel.getPatient().observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(Patient patient) {
                if(patient!=null){
                    EditPatientActivity.this.patient = patient;
                    firstName.setText(patient.firstName);
                    lastName.setText(patient.lastName);
                    department.setText(patient.department);
                    room.setText(patient.room);
                }
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.update(new Patient(firstName.getText().toString(), lastName.getText().toString(), department.getText().toString(), room.getText().toString(), patient.nurseId, patient.patientId));
                Toast.makeText(getApplicationContext(), "updated patient "+patient.getDisplayName(), Toast.LENGTH_LONG).show();
                Intent view = new Intent();
                view.putExtra(PatientActivity.PATIENT_ID, patient.patientId);
                setResult(Activity.RESULT_OK, view);
                finish();
            }
        });
    }

}