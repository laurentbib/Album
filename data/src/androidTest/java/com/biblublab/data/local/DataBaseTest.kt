package com.biblublab.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
abstract class DataBaseTest {
    protected lateinit var appDatabase: AlbumDataBase

    @Before
    fun initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlbumDataBase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }
}