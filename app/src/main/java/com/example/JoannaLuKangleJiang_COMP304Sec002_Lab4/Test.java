package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;


@Entity(tableName = "test",
        foreignKeys = {
                @ForeignKey(entity = Nurse.class,
                        parentColumns = "nurseId",
                        childColumns = "nurseId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Patient.class,
                        parentColumns = "patientId",
                        childColumns = "patientId",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class Test {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int testId;
    @ColumnInfo(index = true)
    int patientId;
    @ColumnInfo(index = true)
    String nurseId;
    double bpl;
    double bph;
    double temperature;

    @Ignore
    public Test(@NotNull int testId, int patientId, String nurseId, double bpl, double bph, double temperature) {
        this.testId = testId;
        this.patientId = patientId;
        this.nurseId = nurseId;
        this.bpl = bpl;
        this.bph = bph;
        this.temperature = temperature;
    }

    public Test(int patientId, String nurseId, double bpl, double bph, double temperature) {
        this.patientId = patientId;
        this.nurseId = nurseId;
        this.bpl = bpl;
        this.bph = bph;
        this.temperature = temperature;
    }

    public String getDisplayName() {
        return
                "Test Id: " + String.valueOf(testId) + "\n"
                        + "BPL: " + String.valueOf(bpl) + "\n"
                        + "BPH: " + String.valueOf(bph) + "\n"
                        + "Temperature: " + String.valueOf(temperature);
    }
}
