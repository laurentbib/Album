package com.biblublab.data.local

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

/**
 * User has one to many relationsShip with Album and one to many with Photo
 *
 */

@Entity(tableName = "album_table", foreignKeys = [ForeignKey(entity = AuthorDatabaseObject::class,
    onDelete = CASCADE, parentColumns = arrayOf("id"), childColumns = arrayOf("authorId"))]
)
data class AlbumDatabaseObject(
    @PrimaryKey val albumId: Int,
     val authorId : Int,
    val title: String
)

@Entity(tableName = "author_table")
data class AuthorDatabaseObject(
    @PrimaryKey val id: Int,
    val name : String,
    val username: String
)

@Entity(tableName = "photo_table")
data class PhotoDatabaseObject(
    @PrimaryKey val photoId: Int,
    val photoAlbumId : Int,
    val url: String,
    val photoTitle: String
)


