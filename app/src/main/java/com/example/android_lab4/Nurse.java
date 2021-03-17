package com.example.android_lab4;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "nurse")
public class Nurse extends Person{
    @PrimaryKey
    @NonNull
    String nurseId;
    String password;

    public Nurse(String firstName, String lastName, String department,@NonNull String nurseId, String password) {
        super(firstName, lastName, department);
        this.nurseId = nurseId;
        this.password = password;
    }
}
