package ru.sergeykamyshov.emotiondiary;

import android.app.Application;
import android.arch.persistence.room.Room;

import ru.sergeykamyshov.emotiondiary.database.AppDatabase;

public class App extends Application {

    public static App instance;

    private AppDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return mDatabase;
    }
}
