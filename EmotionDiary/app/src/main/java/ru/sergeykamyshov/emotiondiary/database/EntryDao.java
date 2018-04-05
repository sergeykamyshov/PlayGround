package ru.sergeykamyshov.emotiondiary.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EntryDao {

    @Query("SELECT * FROM entry")
    List<Entry> getAll();

}
