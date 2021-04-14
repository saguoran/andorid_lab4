package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {

    /// queries for nurses
    @Query("SELECT * FROM stock_info WHERE stockSymbol = :symbol")
    LiveData<StockInfo> getStockBySymbol(String symbol);

    @Query("SELECT * FROM stock_info")
    List<StockInfo> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(StockInfo... stock);

    @Delete
    void delete(StockInfo stockInfo);

}

