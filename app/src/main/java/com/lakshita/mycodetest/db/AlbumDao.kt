package com.lakshita.mycodetest.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lakshita.mycodetest.model.Album

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(albums: List<Album>)

   @Query("SELECT * FROM albums_table ORDER BY album_title ASC")
   fun getAllAlbums(): LiveData<List<Album>>

    @Query("DELETE FROM albums_table")
    fun clear()

    @Query("SELECT * from albums_table WHERE id = :key")
    fun getAlbum(key: Long): Album?


}