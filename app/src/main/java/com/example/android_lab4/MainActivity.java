package com.example.android_lab4;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.android_lab4.ui.login.LoginActivity;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NurseViewModel nurseViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(NurseViewModel.class);
        PatientViewModel patientViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(PatientViewModel.class);
        Nurse nurse = new Nurse("Red", "Iris", "001","iris","123x");
//        Patient patient = new Patient("Red", "Iris", "001",nurse.nurseId, "A21");
//        nurseViewModel.register(nurse);
//        patientViewModel.insert(patient);
        nurseViewModel.login(nurse.nurseId, nurse.password);
        nurseViewModel.getNurseWithPatients().observe(this, (n) ->{

                if(n!=null)
                Log.d(TAG, "onCreate: "+ n.nurse.lastName);
                Log.d(TAG, "onCreate: "+ n);
        }
        );

        findViewById(R.id.login_button).setOnClickListener(v->{
            Intent intent = new Intent(this, LoginActivity.class);
//            intent.putExtra(EXTRA_DATA_UPDATE_WORD, word.getWord());
//            intent.putExtra(EXTRA_DATA_ID, word.getId());
//            startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
           startActivity(intent);
        });
    }
}