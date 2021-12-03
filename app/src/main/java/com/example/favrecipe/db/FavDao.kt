package com.example.favrecipe.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.favrecipe.model.FavDish

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavDish(favDish: FavDish)

    @Query("SELECT * FROM recipes ORDER BY id")
    fun getAllData(): LiveData<List<FavDish>>

}