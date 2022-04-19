package com.example.animemvvm.repository

import com.example.animemvvm.api.RetrofitInstance
import com.example.animemvvm.db.AnimeDatabase
import com.example.animemvvm.modelsT.Top


class AnimeRepository(
    val db: AnimeDatabase
) {
    suspend fun getTopAnimeList(
        page: Int,
    )= RetrofitInstance.api.getTopAnime(page)

    suspend fun searchAnimeList(
        q: String
    ) = RetrofitInstance.api.searchTopAnime(q)

    suspend fun upsert(anime: Top) = db.getAnimeDao().upsert(anime)

    fun getSavedAnime() = db.getAnimeDao().getAllAnimeFromDao()

    suspend fun deleteAnime(anime: Top) = db.getAnimeDao().deleteAnime(anime)


}