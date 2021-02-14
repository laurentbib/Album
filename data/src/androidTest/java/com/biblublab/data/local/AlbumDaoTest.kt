package com.biblublab.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.biblublab.domain.common.*
import com.biblublab.domain.model.Album
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
open class AlbumDaoTest {

    private lateinit var appDatabase: AlbumDataBase
    private lateinit var albumDao: AlbumDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AlbumDataBase::class.java)
                .allowMainThreadQueries()
                .build()
        albumDao = appDatabase.albumDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun insertAlbumTest() = runBlocking {
        //given
        val alBumDatabaseObject = AlbumDatabaseObject(ALBUM_ID, AUTHOR_ID, ALBUM_TITLE)
        val secondAlBumDatabaseObject = AlbumDatabaseObject(ALBUM_ID + ALBUM_ID, AUTHOR_ID, ALBUM_TITLE)
        val authorDatabaseObject = AuthorDatabaseObject(AUTHOR_ID, AUTHOR_NAME, AUTHOR_SURNAME)
        val album = Album(ALBUM_ID, ALBUM_TITLE, AUTHOR_ID, AUTHOR_NAME, AUTHOR_SURNAME)
        val secondAlbum = Album(ALBUM_ID + ALBUM_ID, ALBUM_TITLE, AUTHOR_ID, AUTHOR_NAME, AUTHOR_SURNAME)
        //when
        albumDao.insertAuthors(listOf(authorDatabaseObject))
        albumDao.insertAlbums(listOf(alBumDatabaseObject, secondAlBumDatabaseObject))
        //then
        org.junit.Assert.assertEquals(appDatabase.albumDao().getAllAlbumWithAuthors().take(2).toList(), listOf(album, secondAlbum))

    }


}