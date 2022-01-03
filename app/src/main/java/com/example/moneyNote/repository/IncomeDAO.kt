package com.example.moneyNote.repository

import androidx.room.*
import com.example.moneyNote.module.Income
import com.example.moneyNote.module.Outcome

@Dao
interface IncomeDAO {
    @Insert
    fun insert(income: Income)

    @Update
    fun update(income: Income)

    @Delete
    fun delete(income: Income)

    @Query("SELECT * FROM Incomes WHERE id = :id")
    fun find(id:Int): Income

    @Query("SELECT * FROM Incomes")
    fun findAll():List<Income>

    @Query("SELECT SUM(value) FROM incomes")
    fun getSum(): Int

}