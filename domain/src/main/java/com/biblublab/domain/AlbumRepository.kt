package com.biblublab.domain

import com.biblublab.domain.common.Either
import com.biblublab.domain.model.Album
import com.biblublab.domain.model.AlbumPhoto
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    suspend fun fetchData() : Either<String, Boolean>
    fun fetchAlbums() : Flow<Either<String,List<Album>>>
    fun fetchPhotos(albumId : Int) : Flow<Either<String, List<AlbumPhoto>>>
}