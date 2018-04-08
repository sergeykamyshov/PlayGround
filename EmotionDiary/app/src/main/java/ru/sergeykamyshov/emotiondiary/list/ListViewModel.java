package ru.sergeykamyshov.emotiondiary.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ru.sergeykamyshov.emotiondiary.database.Entry;
import ru.sergeykamyshov.emotiondiary.database.Repository;

public class ListViewModel extends AndroidViewModel {

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Entry>> getData() {
        return Repository.getRepository().getAllEntries();
    }

}
