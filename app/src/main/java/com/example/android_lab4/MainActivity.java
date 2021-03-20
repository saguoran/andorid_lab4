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
    public static final String PATIENT = "PATIENT";
    public static final String NURSE_WITH_PATIENTS = "nurse_with_patients";
    private static final int NURSER_LOGIN = 1;
    private static final int PATIENT_INFO = 2;
    private AppViewModel viewModel;
    private TextView textView;
    private Button button;
    private RecyclerView recyclerView;
    public static final Gson gson =new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AppViewModel.class);
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
                intent.putExtra(PATIENT, gson.toJson(patient));
                startActivityForResult(intent, PATIENT_INFO);
            }
        });
        viewModel.getNurseWithPatients().observe(this, new Observer<NurseWithPatients>() {
            @Override
            public void onChanged(NurseWithPatients nurseWithPatients) {
                if(nurseWithPatients!=null)
                adapter.setPatients(nurseWithPatients.patients);
            }
        });
        button = findViewById(R.id.login_button);
        textView = findViewById(R.id.textView);
        button.setOnClickListener(v -> {
            if (viewModel.authenticated) {
                viewModel.getNurseWithPatients().setValue(null);
                viewModel.authenticated = false;
                button.setText(R.string.action_log_in);
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
                NurseWithPatients nurseWithPatients = gson.fromJson(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(NURSE_WITH_PATIENTS, null), NurseWithPatients.class);
                viewModel.getNurseWithPatients().setValue(nurseWithPatients);
                button.setText(R.string.action_log_out);
                String welcome = String.format("Welcome, %s!", nurseWithPatients.nurse.getDisplayName());
                textView.setText(welcome);
                Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
                viewModel.authenticated = true;
            }else if(requestCode == PATIENT_INFO){
                NurseWithPatients nurseWithPatients = gson.fromJson(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(NURSE_WITH_PATIENTS, null), NurseWithPatients.class);
                viewModel.getNurseWithPatients().setValue(nurseWithPatients);
            }

        }
    }
}