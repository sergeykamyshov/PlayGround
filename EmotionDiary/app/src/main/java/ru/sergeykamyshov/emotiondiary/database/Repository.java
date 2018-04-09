package ru.sergeykamyshov.emotiondiary.database;

import android.arch.lifecycle.LiveData;

import java.util.List;

import ru.sergeykamyshov.emotiondiary.App;
import ru.sergeykamyshov.emotiondiary.database.asynctasks.ClearEntryAsyncTask;
import ru.sergeykamyshov.emotiondiary.database.asynctasks.DeleteByIdAsyncTask;
import ru.sergeykamyshov.emotiondiary.database.asynctasks.InsertEntryAsyncTask;
import ru.sergeykamyshov.emotiondiary.database.asynctasks.UpdateEntryAsyncTask;

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

    public LiveData<Entry> getEntry(long id) {
        EntryDao entryDao = mDatabase.entryDao();
        return entryDao.getEntry(id);
    }

    public void insert(Entry... entry) {
        new InsertEntryAsyncTask().execute(entry);
    }

    public void update(Entry entry) {
        new UpdateEntryAsyncTask().execute(entry);
    }

    public void delete(long id) {
        new DeleteByIdAsyncTask().execute(id);
    }

    public void clear() {
        new ClearEntryAsyncTask().execute();
    }
}
