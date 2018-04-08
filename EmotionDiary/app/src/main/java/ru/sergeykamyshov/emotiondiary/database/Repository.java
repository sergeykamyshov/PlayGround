package ru.sergeykamyshov.emotiondiary.database;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ru.sergeykamyshov.emotiondiary.App;

public class Repository {

    private static Repository mRepository;
    private AppDatabase mDatabase = App.getInstance().getDatabase();

    public static Repository getRepository() {
        if (mRepository == null) {
            mRepository = new Repository();
        }
        return mRepository;
    }

    public LiveData<List<Entry>> getAllEntries() {
        EntryDao entryDao = mDatabase.entryDao();
        return entryDao.getAll();
    }

    public void insert(Entry... entry) {
        new AsyncTask<Entry, Void, Void>() {
            @Override
            protected Void doInBackground(Entry... entries) {
                EntryDao entryDao = App.getInstance().getDatabase().entryDao();
                entryDao.insert(entries);
                return null;
            }
        }.execute(entry);
    }

    public void clear() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                EntryDao entryDao = mDatabase.entryDao();
                entryDao.clear();
                return null;
            }
        }.execute();
    }
}
