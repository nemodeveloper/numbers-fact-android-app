package ru.nemodev.number.fact.repository.db.room;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import ru.nemodev.number.fact.entity.number.NumberFact;

@Dao
public interface NumberFactRepository {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(List<NumberFact> quotes);

    @Transaction
    @Query("SELECT * FROM numbers_fact ORDER BY RANDOM()")
    DataSource.Factory<Integer, NumberFact> getRandom();

    @Transaction
    @Query("SELECT * FROM numbers_fact WHERE number = :number")
    DataSource.Factory<Integer, NumberFact> getByNumber(String number);

    @Query("SELECT COUNT(*) FROM numbers_fact")
    LiveData<Integer> getCount();

}
