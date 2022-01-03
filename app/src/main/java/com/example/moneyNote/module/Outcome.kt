package com.example.moneyNote.module

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "outcomes"
)
data class Outcome(
    @PrimaryKey
    val id: Int? = null,
    @ColumnInfo(name = "value")
    val value: String,
    val description: String,
    @ColumnInfo(name = "user_id")
    val userId:Int,
)
