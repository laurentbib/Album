package com.biblublab.data.local

import androidx.room.*
import com.biblublab.domain.model.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumDatabaseObject>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<PhotoDatabaseObject>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthors(authors: List<AuthorDatabaseObject>)

    @Transaction
    @Query("SELECT alb.albumId, alb.title, alb.authorId, aut.username, aut.name from album_table AS alb INNER JOIN author_table AS aut  ON alb.authorId = aut.id ORDER BY alb.title ")
    fun getAllAlbumWithAuthors() : Flow<List<Album>>

    @Transaction
    @Query("SELECT * from photo_table WHERE photo_table.photoAlbumId = :albumId")
    fun getPhotosFromAlbum(albumId : Int)  : Flow<List<PhotoDatabaseObject>>


}