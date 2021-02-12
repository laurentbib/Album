package com.biblublab.data.remote

import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    @SerializedName("userId")val authorId: Int,
    @SerializedName("id") val albumId: Int,
    val title: String
)

data class AuthorResponse(
    @SerializedName("id") val authorId: Int,
    val name: String,
    val username: String,
)

data class PhotoResponse(
    @SerializedName("id") val photoId: Int,
    @SerializedName("title") val photoTitle: String,
    val albumId: Int,
    val thumbnailUrl: String,
)