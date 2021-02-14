package com.biblublab.domain.model

data class Album(
    val albumId: Int,
    val title: String,
    val authorId: Int,
    val name : String,
    val username : String
)

data class AlbumPhoto(
    val photoId: Int,
    val photoUrl: String,
    val photoTitle: String,
)