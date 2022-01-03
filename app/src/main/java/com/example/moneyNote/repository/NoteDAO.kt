package com.example.moneyNote.repository

import androidx.room.*
import com.example.moneyNote.module.Note
import com.example.moneyNote.module.User

@Dao
interface NoteDAO {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun find(id:Int):Note


    @Query("SELECT * FROM notes")
    fun findAll():List<Note>




}