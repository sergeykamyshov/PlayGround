package ru.sergeykamyshov.emotiondiary.create;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import ru.sergeykamyshov.emotiondiary.database.Entry;
import ru.sergeykamyshov.emotiondiary.database.Repository;

public class CreateEntryViewModel extends ViewModel {

    public LiveData<Entry> getData(long id) {
        return Repository.getRepository().getEntry(id);
    }

}
