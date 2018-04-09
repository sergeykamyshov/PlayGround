package ru.sergeykamyshov.emotiondiary.database.asynctasks;

import android.os.AsyncTask;

import ru.sergeykamyshov.emotiondiary.App;
import ru.sergeykamyshov.emotiondiary.database.EntryDao;

public class ClearEntryAsyncTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        EntryDao entryDao = App.getInstance().getDatabase().entryDao();
        entryDao.clear();
        return null;
    }
}
