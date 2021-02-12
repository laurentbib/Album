package com.biblublab.album.features.album

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumEntity(val albumId : Int ,
                       val title : String,
                       val authorName : String) : Parcelable {
}