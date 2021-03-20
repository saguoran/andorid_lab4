package com.example.android_lab4;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Nurse.class, Patient.class}, version =1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao nurseDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "lab4_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // This callback is called when the database has opened.
    // In this case, use PopulateDbAsync to populate the database
    // with the initial data set if the database has no entries.
    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new Thread(()->{
                        Nurse nurse = new Nurse("Red", "Iris", "001","iris","123x");
                        Patient patient = new Patient("Red", "Iris", "001",nurse.nurseId, "A21");

                        INSTANCE.nurseDao().insertAll(nurse);
                        INSTANCE.nurseDao().insertAll(patient);

                    }).start();
                }
            };

}