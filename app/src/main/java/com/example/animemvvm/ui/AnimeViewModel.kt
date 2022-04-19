package com.example.animemvvm.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.animemvvm.repository.AnimeRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animemvvm.modelsT.JikanV3Response
import com.example.animemvvm.modelsT.Top
import com.example.animemvvm.modelsT.search.JikanV3SearchResponse
import com.example.animemvvm.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class AnimeViewModel(
    val mApplication: Application,
    val mAnimeRepository: AnimeRepository
) : ViewModel(){

    val animeList = MutableLiveData<Resource<JikanV3Response>>()
    private var animeListPage = 1
    private var animeResponse: JikanV3Response? = null

    val searchAnimeList = MutableLiveData<Resource<JikanV3SearchResponse>>()
    private var searchAnimePage = 1
    private var animeSearchResponse: JikanV3SearchResponse? = null

    init {
        getJikanResponse()
    }

    fun getJikanResponse() = viewModelScope.launch {
        animeList.postValue(Resource.Loading())
        val response = mAnimeRepository.getTopAnimeList(animeListPage)
        animeList.postValue(handleJikanResponse(response))
    }

    fun searchJikanResponce(query: String) = viewModelScope.launch {
        searchAnimeList.postValue(Resource.Loading())
        val response: Response<JikanV3SearchResponse> =
            mAnimeRepository.searchAnimeList(query, searchAnimePage)
        searchAnimeList.postValue(handleSearchJikanResponse(response))
    }

    private fun handleJikanResponse(response: Response<JikanV3Response>): Resource<JikanV3Response> {
        if (response.isSuccessful){
            response.body()?.let {
                animeListPage++
                if (animeResponse == null){
                    animeResponse = it
                }else{
                    val oldAnimeList = animeResponse?.top
                    val newAnimeList = it.top
                    oldAnimeList?.addAll(newAnimeList)
                }
                return Resource.Success(animeResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchJikanResponse(response: Response<JikanV3SearchResponse>): Resource<JikanV3SearchResponse> {
        if (response.isSuccessful){
            response.body()?.let {
                searchAnimePage++
                if (animeSearchResponse == null){
                    animeSearchResponse = it
                }else{
                    val oldAnimeList = animeSearchResponse?.results
                    val newAnimeList = it.results
                    oldAnimeList?.addAll(newAnimeList)
                }
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveAnime(anime: Top) = viewModelScope.launch {
        mAnimeRepository.upsert(anime)
    }

    fun getSavedAnime() = mAnimeRepository.getSavedAnime()

    fun deleteAnime(anime: Top) = viewModelScope.launch {
        mAnimeRepository.deleteAnime(anime)
    }


}