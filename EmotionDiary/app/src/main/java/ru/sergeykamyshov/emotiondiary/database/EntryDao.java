package ru.sergeykamyshov.emotiondiary.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface EntryDao {

    @Query("select * from entry")
    LiveData<List<Entry>> getAll();

    @Query("select * from entry where id = :id")
    LiveData<Entry> getEntry(long id);

    @Insert
    void insert(Entry... entry);

    @Update
    void update(Entry... entry);

    @Query("delete from entry where id = :id")
    void delete(long id);

    @Query("delete from entry")
    void clear();
}
