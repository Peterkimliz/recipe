package com.example.favrecipe.repository

import androidx.lifecycle.LiveData
import com.example.favrecipe.db.FavDatabase
import com.example.favrecipe.model.FavDish

class FavRepository(private val favDatabase: FavDatabase) {

    suspend fun insertData(favDish: FavDish) {
        favDatabase.getFav().insertFavDish(favDish)
    }

    val allDishesList: LiveData<List<FavDish>> =favDatabase.getFav().getAllData()
    }