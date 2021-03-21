package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "patient",
        foreignKeys = {@ForeignKey(entity = Nurse.class,
                parentColumns = "nurseId",
                childColumns = "nurseId",
                onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index(value = {"first_name", "last_name"},
                unique = true)}
)
public class Patient extends Person {
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

    @Ignore
    public Patient(String firstName, String lastName, String department, String room, String nurseId, int patientId) {
        super(firstName, lastName, department);
        this.patientId = patientId;
        this.nurseId = nurseId;
        this.room = room;
    }
}
