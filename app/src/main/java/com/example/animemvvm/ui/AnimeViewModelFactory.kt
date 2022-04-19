package com.example.animemvvm.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animemvvm.repository.AnimeRepository


class AnimeViewModelFactory(private val application: Application, private val param: AnimeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimeViewModel(application, param) as T
    }
}