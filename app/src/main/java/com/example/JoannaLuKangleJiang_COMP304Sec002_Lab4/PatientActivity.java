package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

public class PatientActivity extends AppCompatActivity {

    private static final String TAG = "PatientActivity";
    public static final String PATIENT_ID = "EditPatient";
    public static final int EDIT_PATIENT = 4;

    AppViewModel viewModel;
    Patient patient;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        TextView firstName = findViewById(R.id.full_name);
        TextView department = findViewById(R.id.department);
        TextView room = findViewById(R.id.room);

        // obtain patient id
        Intent intent = getIntent();
        int patientId = intent.getIntExtra(MainActivity.VIEW_PATIENT, -1);

        // view model
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AppViewModel.class);
        viewModel.getPatientById(patientId);

        // update text values
        viewModel.getPatient().observe(this, new Observer<Patient>() {
            @Override
            public void onChanged(Patient patient) {
                if (patient != null) {
                    PatientActivity.this.patient = patient;
                    firstName.setText(("Full Name: " + patient.getDisplayName()));
                    department.setText(("Department: " + patient.department));
                    room.setText(("Room: " + patient.room));
                }
            }
        });

        // setOnClickListener for edit patient
        findViewById(R.id.edit_patient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientActivity.this, EditPatientActivity.class);
                intent.putExtra(PATIENT_ID, patient.patientId);
                startActivityForResult(intent, EDIT_PATIENT);
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

    // send to TestActivity
    public void btnNewTest_OnClick(View view) {

        // move to Test Activity
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra(MainActivity.VIEW_PATIENT, gson.toJson(patient));
        startActivity(intent);
    }

    // send to ViewTestInfoActivity
    public void btnViewTestInfo_OnClick(View view) {

        // move to Test Activity
        Intent intent = new Intent(this, ViewTestInfoActivity.class);
        intent.putExtra(MainActivity.VIEW_PATIENT, gson.toJson(patient));
        startActivity(intent);

    }
}