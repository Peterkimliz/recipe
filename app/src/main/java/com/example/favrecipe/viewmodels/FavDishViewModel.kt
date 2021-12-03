package com.example.favrecipe.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favrecipe.model.FavDish
import com.example.favrecipe.repository.FavRepository
import kotlinx.coroutines.launch

class FavDishViewModel(private val repository: FavRepository ):ViewModel(){

    fun insertData(favDish: FavDish)=viewModelScope.launch {
        repository.insertData(favDish)
    }
    val allDishesList: LiveData<List<FavDish>> =repository.allDishesList

}