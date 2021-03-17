package com.example.android_lab4;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "patient")
public class Patient extends Person{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    String patientId;
    String nurseId;
    String room;

    public Patient(String firstName, String lastName, String department, @NonNull String patientId, String nurseId, String room) {
        super(firstName, lastName, department);
        this.patientId = patientId;
        this.nurseId = nurseId;
        this.room = room;
    }
}
