package com.biblublab.album.features.gallery

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoEntity(
    val photoId: Int,
    val photoUrl: String,
    val photoTitle: String
) : Parcelable