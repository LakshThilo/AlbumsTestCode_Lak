package com.lakshita.mycodetest.network

import com.lakshita.mycodetest.model.Album
import com.lakshita.mycodetest.utils.APIEndPoint
import retrofit2.http.GET

interface AlbumsApiService {
    @GET(APIEndPoint.albumsURL)
    suspend fun getAlbums(): List<Album>
}


