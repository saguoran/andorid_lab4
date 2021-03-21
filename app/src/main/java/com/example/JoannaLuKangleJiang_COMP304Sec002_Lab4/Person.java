package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
abstract class Person  {
    @ColumnInfo(name = "first_name")
    String firstName;
    @ColumnInfo(name = "last_name")
    String lastName;
    String department;

    public Person(String firstName, String lastName, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }
    public String getDisplayName(){
        return firstName+" "+lastName;
    }

}
