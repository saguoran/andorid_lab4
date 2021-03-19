package com.example.android_lab4;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "test",
        foreignKeys = {@ForeignKey(entity = Nurse.class,
                parentColumns = "nurseId",
                childColumns = "nurseId",
                onDelete = ForeignKey.CASCADE)
        }
)
public class Test {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    String testId;
    String patientId;
    String nurseId;
    double bpl;
    double bph;
    double temperature;

    public Test(String testId, String patientId, String nurseId, double bpl, double bph, double temperature ) {

        this.testId = testId;
        this.patientId = patientId;
        this.nurseId = nurseId;
        this.bpl = bpl;
        this.bph = bph;
        this.temperature = temperature;
    }
}
