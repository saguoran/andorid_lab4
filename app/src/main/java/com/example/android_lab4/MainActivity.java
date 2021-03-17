package com.example.android_lab4;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NurseViewModel nurseViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(NurseViewModel.class);
        Nurse nurse = new Nurse("Red", "Iris", "001","irisHS","123x");
        nurseViewModel.register(nurse);
        nurseViewModel.login(nurse.nurseId, nurse.password);
        LiveData<Nurse> nurseLiveData = nurseViewModel.getNurseLiveData();
        nurseLiveData.observe(this, (n) ->
                Log.d(TAG, "onCreate: "+ n.lastName));
    }
}