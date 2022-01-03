package com.example.moneyNote.repository

import androidx.room.*
import com.example.moneyNote.module.Note
import com.example.moneyNote.module.User

@Dao
interface UserDAO {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    fun find(id:Int):User

    @Query("SELECT * FROM users WHERE email = :email")
    fun findByEmail(email:String):User

    @Query("SELECT * FROM users")
    fun findAll():List<User>




}