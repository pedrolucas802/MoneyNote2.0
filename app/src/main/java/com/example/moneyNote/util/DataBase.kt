package com.example.moneyNote.util

import android.content.Context
import androidx.room.Room
import com.example.moneyNote.repository.NoteDatabase

object DataBase {

    private var instance: NoteDatabase? = null


    fun getInstance(context: Context): NoteDatabase{

        if(instance == null){
            instance = Room.databaseBuilder(
                context,
                NoteDatabase::class.java,
                "note.db"
            )
//                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

        return instance!!
    }


}