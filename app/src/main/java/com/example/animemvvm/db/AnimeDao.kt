package com.example.animemvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.animemvvm.modelsT.Top

@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(animeObj: Top)

    @Query("SELECT * FROM top")
    fun getAllAnimeFromDao(): LiveData<List<Top>>

    @Delete
    suspend fun deleteAnime(anime: Top)

}