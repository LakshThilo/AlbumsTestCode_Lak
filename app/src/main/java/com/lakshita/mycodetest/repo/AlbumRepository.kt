package com.lakshita.mycodetest.repo

import androidx.lifecycle.LiveData
import com.lakshita.mycodetest.db.AlbumDao
import com.lakshita.mycodetest.model.Album
import com.lakshita.mycodetest.network.AlbumsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository(private val albumDao: AlbumDao, private val albumsApiService: AlbumsApiService) {

    val albums: LiveData<List<Album>> = albumDao.getAllAlbums()

    suspend fun refreshAlbums() {
        withContext(Dispatchers.IO) {
            val albums = albumsApiService.getAlbums()
            albumDao.insertAll(albums)
        }
    }
}
