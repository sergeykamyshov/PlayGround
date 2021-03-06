package ru.sergeykamyshov.emotiondiary.database.asynctasks;

import android.os.AsyncTask;

import ru.sergeykamyshov.emotiondiary.App;
import ru.sergeykamyshov.emotiondiary.database.Entry;
import ru.sergeykamyshov.emotiondiary.database.EntryDao;

public class InsertEntryAsyncTask extends AsyncTask<Entry, Void, Void> {
    @Override
    protected Void doInBackground(Entry... entries) {
        EntryDao entryDao = App.getInstance().getDatabase().entryDao();
        entryDao.insert(entries);
        return null;
    }
}
