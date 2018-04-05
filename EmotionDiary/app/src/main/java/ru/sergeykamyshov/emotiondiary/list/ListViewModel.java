package ru.sergeykamyshov.emotiondiary.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykamyshov.emotiondiary.database.Entry;

public class ListViewModel extends ViewModel {

    private MutableLiveData<List<Entry>> mLiveData;

    public LiveData<List<Entry>> getData() {
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<>();
            loadTestData();
        }
        return mLiveData;
    }

    private void loadTestData() {
        // generate test data
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Entry entry = new Entry();
            entry.setSituation("Situation " + i);
            entries.add(entry);
        }
        mLiveData.postValue(entries);
    }

}
