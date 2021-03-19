package com.example.android_lab4;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "patient",
        foreignKeys = {@ForeignKey(entity = Nurse.class,
        parentColumns = "nurseId",
        childColumns = "nurseId",
        onDelete = ForeignKey.CASCADE)
        }
)
public class Patient extends Person{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int patientId;
    @ColumnInfo(index = true)
    String nurseId;
    String room;

    public Patient(String firstName, String lastName, String department, String nurseId, String room) {
        super(firstName, lastName, department);
        this.nurseId = nurseId;
        this.room = room;
    }
}
