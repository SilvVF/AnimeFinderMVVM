package com.example.animemvvm.api

import com.example.animemvvm.modelsT.JikanV3Response
import com.example.animemvvm.modelsT.search.JikanV3SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TopAnimeApi {

    @GET("top/anime/{page}")
    suspend fun getTopAnime(
        @Path("page")
        page: Int,

    ) : Response<JikanV3Response>

    @GET("search/anime")
    suspend fun searchTopAnime(
        @Query("q")
        q: String?
    ) : Response<JikanV3SearchResponse>

}
