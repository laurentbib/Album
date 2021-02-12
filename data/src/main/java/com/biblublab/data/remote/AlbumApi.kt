package com.biblublab.data.remote

import com.biblublab.domain.common.PARAM_ALBUM_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumApi {

    @GET("albums")
    suspend fun getAlbums() : Response<List<AlbumResponse>>

    @GET("photos")
    suspend fun getPhotos() : Response<List<PhotoResponse>>

    @GET("photos")
    suspend fun getPhotosByAlbum(@Query(PARAM_ALBUM_ID) albumId: Int) : Response<List<PhotoResponse>>

    @GET("users")
    suspend fun getAuthors() : Response<List<AuthorResponse>>

}