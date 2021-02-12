package com.biblublab.data.repository

import com.biblublab.data.remote.AlbumResponse
import com.biblublab.domain.common.ALBUM_ID
import com.biblublab.domain.common.ALBUM_TITLE
import com.biblublab.domain.common.AUTHOR_ID
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AlbumMapperTest {

    private lateinit var albumMapper: AlbumMapper

    @Before
    fun setUp() {
        albumMapper = AlbumMapper()
    }

    @Test
    fun responseAlbumObjectIsTheSameAsDataBaseAlbumObject() {
        val response = AlbumResponse(AUTHOR_ID, ALBUM_ID, ALBUM_TITLE)

        val result = albumMapper.toAlbumDatabaseObject(response)

        assertEquals(result.albumId, response.albumId)
    }
}