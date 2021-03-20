package com.example.android_lab4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

public class PatientActivity extends AppCompatActivity {
    private static final String TAG = "PatientActivity";
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        AppViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AppViewModel.class);
        Intent intent = getIntent();
        patient = MainActivity.gson.fromJson(intent.getStringExtra(MainActivity.PATIENT), Patient.class);
        EditText firstName = findViewById(R.id.first_name);
        firstName.setText(patient.firstName);
        EditText lastName = findViewById(R.id.last_name);
        lastName.setText(patient.lastName);
        EditText department = findViewById(R.id.department);
        department.setText(patient.department);
        EditText room = findViewById(R.id.room);
        room.setText(patient.room);
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.update(new Patient(firstName.getText().toString(), lastName.getText().toString(), department.getText().toString(), room.getText().toString(), patient.nurseId, patient.patientId));
                Toast.makeText(getApplicationContext(), "updated patient "+patient.getDisplayName(), Toast.LENGTH_LONG).show();
            }
        });
        viewModel.getNurseWithPatients().observe(this, (n) -> {
                    if (n != null) {
                        Log.d(TAG, "onCreate: " + n.nurse.getDisplayName());
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                                .putString(MainActivity.NURSE_WITH_PATIENTS, MainActivity.gson.toJson(n)).apply();
                        Intent view = new Intent();
                        setResult(Activity.RESULT_OK, view);
                        finish();
                    }
                }
        );

    }
}