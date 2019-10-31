package ru.nemodev.number.fact.repository.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import ru.nemodev.number.fact.entity.NumberFact;

@Dao
public interface NumberFactRepository {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(List<NumberFact> quotes);

    @Transaction
    @Query("SELECT * FROM numbers_fact ORDER BY RANDOM() LIMIT :count")
    List<NumberFact> getRandom(int count);

    @Transaction
    @Query("SELECT * FROM numbers_fact WHERE number = :number")
    List<NumberFact> getByNumber(String number);

}
