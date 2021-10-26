package com.lakshita.mycodetest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lakshita.mycodetest.model.Album

@Database(entities = [Album::class], version = AlbumDatabase.VERSION, exportSchema = false)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun getAlbumDao() : AlbumDao

    companion object {
        const val VERSION = 1
    }

}