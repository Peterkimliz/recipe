package com.example.favrecipe.application

import android.app.Application
import com.example.favrecipe.db.FavDatabase
import com.example.favrecipe.repository.FavRepository

class FavDishApplication:Application(){

    private val database by lazy {
        FavDatabase(this@FavDishApplication)
    }

    val repository by lazy {
        FavRepository(database)
    }
}