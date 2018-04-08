package ru.sergeykamyshov.emotiondiary.create;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Date;

import ru.sergeykamyshov.emotiondiary.database.Entry;

public class CreateEntryViewModel extends ViewModel {

    MutableLiveData<Entry> mLiveData;

    public LiveData<Entry> getData() {
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<>();
//            loadData();
        }
        return mLiveData;
    }

    private void loadData() {
        Entry entry = new Entry();
        entry.setSituation("Test situation");
        entry.setThoughts("Test thoughts");
        entry.setEmotion("Test emotions");
        entry.setReaction("Test reaction");
        entry.setDate(new Date().getTime());

        mLiveData.setValue(entry);
    }

}
