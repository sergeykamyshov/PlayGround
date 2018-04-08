package ru.sergeykamyshov.emotiondiary.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EntryDao {

    @Query("select * from entry")
    LiveData<List<Entry>> getAll();

    @Insert
    void insert(Entry... entry);

    @Query("delete from entry")
    void clear();
}
