package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Nurse.class, Patient.class, Test.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    // This callback is called when the database has opened.
    // In this case, use PopulateDbAsync to populate the database
    // with the initial data set if the database has no entries.
    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new Thread(() -> {
                        Nurse nurse = new Nurse("Red", "Iris", "001", "iris", "123x");
                        Patient[] patients = {
                                new Patient("Cassia", "Cambria", "000", nurse.nurseId, "A20"),
                                new Patient("Adriel", "Caspian", "001", nurse.nurseId, "A21"),
                                new Patient("Arcadia", "Adriel", "002", nurse.nurseId, "A22"),
                                new Patient("Caspian", "Aurelia", "003", nurse.nurseId, "A23"),
                                new Patient("Aiyana", "Altair", "004", nurse.nurseId, "A24"),
//                                new Patient("Avalon", "Brielle", "005", nurse.nurseId, "A25"),
//                                new Patient("Cambria", "Aurelian", "006", nurse.nurseId, "A26"),
//                                new Patient("Cara", "Alissa", "000", nurse.nurseId, "A27"),
//                                new Patient("Aurelia", "Adair", "001", nurse.nurseId, "A28"),
//                                new Patient("Anya", "Anatola", "002", nurse.nurseId, "A29"),
//                                new Patient("Carys", "Abrielle", "003", nurse.nurseId, "A210"),
//                                new Patient("Adara", "Adara", "004", nurse.nurseId, "A211"),
                        };
                        Test[] tests ={
                                new Test(1,"iris",111,222,30),
                                new Test(1,"iris",222,333,31),
                                new Test(1,"iris",333,444,32),
                        };
                        INSTANCE.appDao().insertAll(nurse);
                        INSTANCE.appDao().insertAll(patients);
                        INSTANCE.appDao().insertAll(tests);
                    }).start();
                }
            };

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

    public abstract AppDao appDao();

}