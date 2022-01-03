package com.example.moneyNote.repository

import androidx.room.*
import com.example.moneyNote.module.Income
import com.example.moneyNote.module.Outcome

@Dao
interface OutcomeDAO {
    @Insert
    fun insert(outcome: Outcome)

    @Update
    fun update(outcome: Outcome)

    @Delete
    fun delete(outcome: Outcome)

    @Query("SELECT * FROM outcomes WHERE id = :id")
    fun find(id:Int): Outcome

    @Query("SELECT * FROM outcomes")
    fun findAll():List<Outcome>

//    @Query("SELECT SUM( outcome ) as outcomeSum  +  FROM  + outcomes")
//    fun getOutcomeSum(): Double

    @Query("SELECT SUM(value) FROM outcomes")
    fun getSum(): Int


}