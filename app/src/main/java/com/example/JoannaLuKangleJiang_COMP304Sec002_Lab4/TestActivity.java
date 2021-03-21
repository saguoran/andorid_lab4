package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    Test test;
    Patient patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        AppViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AppViewModel.class);

        //LiveData<List<Test>> tests = viewModel.getAllTestLiveData();

        // linked the activity objects
        TextView firstName = findViewById(R.id.patient_first_name);
        TextView lastName = findViewById(R.id.patient_last_name);
        EditText bph = findViewById(R.id.text_BPH);
        EditText bpl = findViewById(R.id.text_BPL);
        EditText temperature = findViewById(R.id.text_Temperature);

        // obtain patient info
        Intent intent = getIntent();
        patient = MainActivity.gson.fromJson(intent.getStringExtra(MainActivity.VIEW_PATIENT), Patient.class);
        int patientId = patient.patientId;
        String nurserId = patient.nurseId;

        // populate patient info
        firstName.setText(patient.firstName);
        lastName.setText(patient.lastName);

        findViewById(R.id.btnTestSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double bphVale = Double.parseDouble(bph.getText().toString());
                double bplVale = Double.parseDouble(bpl.getText().toString());
                double temperatureVale = Double.parseDouble(temperature.getText().toString());

                Test test = new Test(patientId,nurserId,bphVale,bplVale,temperatureVale);
                viewModel.newTest(test);

                Toast.makeText(getApplicationContext(), "Saved test for" + patient.getDisplayName(), Toast.LENGTH_LONG).show();
                Intent view = new Intent();
                view.putExtra(PatientActivity.PATIENT_ID, patient.patientId);
                setResult(Activity.RESULT_OK, view);
                finish();
            }
        });
    }
}