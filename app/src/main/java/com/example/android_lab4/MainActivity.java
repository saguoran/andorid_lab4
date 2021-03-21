package com.example.android_lab4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String VIEW_PATIENT = "view_patient";
    public static final String NEW_PATIENT = "new_patient";
    public static final String NURSE_WITH_PATIENTS = "nurse_with_patients";
    private static final int NURSER_LOGIN = 1;
    private static final int PATIENT_INFO = 2;
    private AppViewModel viewModel;
    private TextView textView;
    private Button signUpLoginButton;
    private Button addPatientButton;
    private RecyclerView recyclerView;
    public static final Gson gson =new Gson();
    private Nurse nurse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AppViewModel.class);
        addPatientButton = findViewById(R.id.add_patient);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        final CustomAdapter adapter = new CustomAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setOnItemClickListener(new CustomAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Patient patient = adapter.getPatientAtPosition(position);
                Intent intent = new Intent(MainActivity.this, PatientActivity.class);
                intent.putExtra(VIEW_PATIENT, patient.patientId);
                startActivityForResult(intent, PATIENT_INFO);
            }
        });
        viewModel.getNurseWithPatients().observe(this, new Observer<NurseWithPatients>() {
            @Override
            public void onChanged(NurseWithPatients nurseWithPatients) {
                if(nurseWithPatients!=null){
                    nurse = nurseWithPatients.nurse;
                    adapter.setPatients(nurseWithPatients.patients);
                    String welcome = String.format("Welcome, %s!", nurseWithPatients.nurse.getDisplayName());
                    textView.setText(welcome);
                    Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
                    addPatientButton.setEnabled(true);
                    recyclerView.setVisibility(View.VISIBLE);
                }else {
                    addPatientButton.setEnabled(false);
                }
            }
        });
        signUpLoginButton = findViewById(R.id.login_button);
        textView = findViewById(R.id.textView);
        signUpLoginButton.setOnClickListener(v -> {
            if (viewModel.authenticated) {
                viewModel.getNurseWithPatients().setValue(null);
                viewModel.authenticated = false;
                signUpLoginButton.setText(R.string.action_log_in);
                textView.setText("");
                recyclerView.setVisibility(View.GONE);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, NURSER_LOGIN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == NURSER_LOGIN) {
                String nurseId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(NURSE_WITH_PATIENTS, null);
                viewModel.authenticated = true;
                viewModel.findNurseWithPatientsByNurseId(nurseId);
                signUpLoginButton.setText(R.string.action_log_out);
            }
        }
        /// refresh page
        if(nurse!=null){
            viewModel.login(nurse.nurseId, nurse.password);
        }
    }
}