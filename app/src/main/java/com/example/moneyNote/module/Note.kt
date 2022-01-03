package com.example.moneyNote.module

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes",
    indices = [
        Index("name", unique = true)
    ]
)
data class Note(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val description: String,
    @ColumnInfo(name = "user_id")
    val userId:Int,
    @ColumnInfo(name = "note_id")
    val noteId:Int
)
