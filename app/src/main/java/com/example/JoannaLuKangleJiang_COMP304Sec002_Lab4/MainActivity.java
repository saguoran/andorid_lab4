package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String currentMessage;
    private static final int SMS_RECEIVE_PERMISSION_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_PHONE_STATE},
                SMS_RECEIVE_PERMISSION_REQUEST);
        // init variables
        AppViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AppViewModel.class);
        findViewById(R.id.insert_button).setOnClickListener(v -> {
            String[] amazon = getResources().getStringArray(R.array.amazon);
            String[] ssnlf = getResources().getStringArray(R.array.ssnlf);
            StockInfo[] stockInfos = {new StockInfo(amazon[0], amazon[1], Double.parseDouble(amazon[2])), new StockInfo(ssnlf[0], ssnlf[1], Double.parseDouble(ssnlf[2])) };
            viewModel.getRepo().insert(stockInfos);
        });
        findViewById(R.id.button).setOnClickListener(v -> {
            //Get the SmsManager instance and call the sendTextMessage method to send message
            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage("5554", null, currentMessage,null,null);
            Toast.makeText(getApplicationContext(), currentMessage,
                    Toast.LENGTH_LONG).show();
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TextView textView = findViewById(R.id.stock_textView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        final CustomAdapter adapter = new CustomAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewModel.getRepo().get_stockInfos().observe(this, stockInfos -> {
            if(stockInfos!=null){
                adapter.setStockInfos(stockInfos);
                adapter.setOnItemClickListener(new CustomAdapter.ClickListener() {
                    @Override
                    public void onItemClick(View v, StockInfo stockInfo) {
                        Log.d(TAG, "onItemClick: "+stockInfo.getCompanyName());
                        textView.setText(stockInfo.toString());
                        currentMessage = stockInfo.toString();
                    }
                });
            }
        });

    }
}