package com.example.animemvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.animemvvm.modelsT.Top

@Database(
    entities = [Top::class],
    version = 1
)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun getAnimeDao() : AnimeDao

    companion object{
        @Volatile
        private var instance: AnimeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: createDatabsase(context).also { instance = it }
        }

        private fun createDatabsase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AnimeDatabase::class.java,
                "anime_db.db"
            ).build()

    }

}