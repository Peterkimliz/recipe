package com.example.favrecipe.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favrecipe.repository.FavRepository
import com.example.favrecipe.viewmodels.FavDishViewModel

class FavDishViewModelFactory(private val repository: FavRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavDishViewModel::class.java)) {
            return FavDishViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")

    }
}