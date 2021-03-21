package com.example.android_lab4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;

import static com.example.android_lab4.MainActivity.NURSE_WITH_PATIENTS;

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
                if (patient != null) {
                    EditPatientActivity.this.patient = patient;
                    firstName.setText(patient.firstName);
                    lastName.setText(patient.lastName);
                    department.setText(patient.department);
                    room.setText(patient.room);
                } else {
                    String nurseId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(NURSE_WITH_PATIENTS, null);
                    EditPatientActivity.this.patient = new Patient("", "", "", nurseId, "");
                    patient = EditPatientActivity.this.patient;
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
                if (patient.patientId > 0)
                    viewModel.update(new Patient(firstName.getText().toString(), lastName.getText().toString(), department.getText().toString(), room.getText().toString(), patient.nurseId, patient.patientId));
                else
                    viewModel.insert(new Patient(firstName.getText().toString(), lastName.getText().toString(), department.getText().toString(), patient.nurseId, room.getText().toString()));
                Toast.makeText(getApplicationContext(), "Saved patient " + patient.getDisplayName(), Toast.LENGTH_LONG).show();
                Intent view = new Intent();
                view.putExtra(PatientActivity.PATIENT_ID, patient.patientId);
                setResult(Activity.RESULT_OK, view);
                finish();
            }
        });
    }
}