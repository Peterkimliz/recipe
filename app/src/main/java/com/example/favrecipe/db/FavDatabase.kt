package com.example.favrecipe.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.favrecipe.model.FavDish

@Database(entities = [FavDish::class], version = 1)
abstract class FavDatabase : RoomDatabase() {

    abstract fun getFav(): FavDao
    companion object {
        @Volatile
        private var instance: FavDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }

        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FavDatabase::class.java,
            "articles.db"
        ).build()
    }

}


