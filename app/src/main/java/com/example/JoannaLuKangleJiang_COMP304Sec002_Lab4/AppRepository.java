package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class AppRepository {

    private final AppDao appDao;
    private final MutableLiveData<List<StockInfo>> _stockInfos =new MutableLiveData<>();

    // constructor for AppRepository
    AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        appDao = db.appDao();
        new Thread(()->{
            if(appDao.getAll().isEmpty())
                appDao.insertAll(new StockInfo("GOOGL", "Google", 2254.43));
            _stockInfos.postValue( appDao.getAll());
        }).start();
    }

    // return stockInfos
    public MutableLiveData<List<StockInfo>> get_stockInfos() {
        return _stockInfos;
    }

    // insert nurse
    public void insert(StockInfo... stockInfos) {
        new Thread(() -> {
            for (StockInfo stockInfo :
                    stockInfos) {
                appDao.insertAll(stockInfo);
                this._stockInfos.postValue(appDao.getAll());
            }
        }).start();
    }
}
