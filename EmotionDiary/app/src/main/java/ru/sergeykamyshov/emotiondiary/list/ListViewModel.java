package ru.sergeykamyshov.emotiondiary.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ru.sergeykamyshov.emotiondiary.App;
import ru.sergeykamyshov.emotiondiary.database.Entry;

public class ListViewModel extends AndroidViewModel {

//    private MutableLiveData<List<Entry>> mLiveData;

    public ListViewModel(@NonNull Application application) {
        super(application);
//        loadData();
    }

    public LiveData<List<Entry>> getData() {
//        if (mLiveData == null) {
//            mLiveData = new MutableLiveData<>();
//        }
//        return mLiveData;

        return App.getInstance().getDatabase().entryDao().getAll();
    }

    public void loadData() {

    }

}
