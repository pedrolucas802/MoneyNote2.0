package com.example.moneyNote.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneyNote.module.Income
import com.example.moneyNote.module.Outcome
import com.example.moneyNote.module.User

@Database(entities = [User::class, Income::class, Outcome::class], version = 2)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDAO

    abstract fun getIncomeDao(): IncomeDAO

    abstract fun getOutcomeDao(): OutcomeDAO

}