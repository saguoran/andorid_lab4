package com.example.android_lab4;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class NurseWithPatients {
    @Embedded
    public Nurse nurse;
    @Relation(
            parentColumn = "nurseId",
            entityColumn = "nurseId"
    )
    public List<Patient> patients;
}