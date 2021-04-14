package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stock_info")
public class StockInfo {
    @PrimaryKey
    @NonNull
    private String stockSymbol; //primary key private String companyName;
    private String companyName;
    private double stockQuote; // stock value

    public StockInfo(@NonNull String stockSymbol, String companyName, double stockQuote) {
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.stockQuote = stockQuote;
    }

    @NonNull
    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(@NonNull String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getStockQuote() {
        return stockQuote;
    }

    public void setStockQuote(double stockQuote) {
        this.stockQuote = stockQuote;
    }

    @Override
    public String toString() {
        return
                "Stock Symbol:" + stockSymbol +
                "\nCompany Name:" + companyName +
                "\nStock Quote:" + stockQuote
               ;
    }
}
