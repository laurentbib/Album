package com.biblublab.data.repository

import com.biblublab.data.local.AlbumDatabaseObject
import com.biblublab.data.local.AuthorDatabaseObject
import com.biblublab.data.local.PhotoDatabaseObject
import com.biblublab.data.remote.AlbumResponse
import com.biblublab.data.remote.AuthorResponse
import com.biblublab.data.remote.PhotoResponse
import com.biblublab.domain.model.AlbumPhoto


class AlbumMapper {

    fun toAlbumDatabaseObject(albumResponse: AlbumResponse): AlbumDatabaseObject = with(albumResponse) {
        AlbumDatabaseObject(
            albumId = albumId,
            authorId = authorId,
            title = title
        )
    }

     fun toAuthorDataBaseObject(authorResponse: AuthorResponse) = with(authorResponse) {
        AuthorDatabaseObject(
            id = authorId,
            name = name,
            username = username
        )
    }

    fun toPhotoDataBaseObject(photoResponse: PhotoResponse) = with(photoResponse){
        PhotoDatabaseObject(
            photoId = photoId,
            photoTitle = photoTitle,
            url = thumbnailUrl,
            photoAlbumId = albumId
        )
    }

    fun toPhotoAlbumFromDb(photoDatabaseObject: PhotoDatabaseObject) = with(photoDatabaseObject){
        AlbumPhoto(photoId = photoId,
        photoTitle = photoTitle,
        photoUrl = url)
    }

}