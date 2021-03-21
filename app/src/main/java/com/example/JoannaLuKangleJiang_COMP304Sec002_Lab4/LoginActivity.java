package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AppViewModel.class);
        Button button = findViewById(R.id.sign_up_button);
        button.setOnClickListener(v -> {
            String nurseId = ((EditText) findViewById(R.id.editTextTextPersonName)).getText().toString();
            String password = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();
            viewModel.login(nurseId, password);
        });
        viewModel.getNurseWithPatients().observe(this, (n) -> {
                    if (n != null) {
                        Log.d(TAG, "onCreate: " + n.nurse.getDisplayName());
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                                .putString(MainActivity.NURSE_WITH_PATIENTS, n.nurse.nurseId).apply();
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Wrong credential", Toast.LENGTH_LONG).show();
                }
        );
    }
}