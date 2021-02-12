package com.biblublab.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AlbumDatabaseObject::class, PhotoDatabaseObject::class, AuthorDatabaseObject::class], version = 1)
abstract class AlbumDataBase : RoomDatabase() {

    companion object{
        const val DB_NAME = "Album_data_base"
    }

    abstract fun albumDao() : AlbumDao
}