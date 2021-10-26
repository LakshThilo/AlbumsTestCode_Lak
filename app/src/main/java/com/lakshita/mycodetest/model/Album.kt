package com.lakshita.mycodetest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "albums_table")
data class Album(

    @ColumnInfo(name = "user_id")
    @Json(name = "userId") val userId: Int,

    @PrimaryKey(autoGenerate = false)
    @Json(name = "id") val id: Int,

    @ColumnInfo(name = "album_title")
    @Json(name = "title") val title: String
)
