package ru.sergeykamyshov.emotiondiary.database.asynctasks;

import android.os.AsyncTask;

import ru.sergeykamyshov.emotiondiary.App;
import ru.sergeykamyshov.emotiondiary.database.EntryDao;

public class DeleteByIdAsyncTask extends AsyncTask<Long, Void, Void> {
    @Override
    protected Void doInBackground(Long... ids) {
        EntryDao entryDao = App.getInstance().getDatabase().entryDao();
        entryDao.delete(ids[0]);
        return null;
    }
}
