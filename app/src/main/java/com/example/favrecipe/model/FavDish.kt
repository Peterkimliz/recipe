package com.example.favrecipe.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipes")
data class FavDish(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val image: String,
    @ColumnInfo val imageSource: String,
    @ColumnInfo val title: String,
    @ColumnInfo val type: String,
    @ColumnInfo val category: String,
    @ColumnInfo val ingredients: String,
    @ColumnInfo val cookingTime: String,
    @ColumnInfo val directionToCook: String,
    @ColumnInfo var favouriteDish: Boolean = false
):Parcelable