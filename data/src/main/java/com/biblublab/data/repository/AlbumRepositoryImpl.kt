package com.biblublab.data.repository

import com.biblublab.data.local.AlbumDao
import com.biblublab.data.remote.AlbumApi
import com.biblublab.domain.AlbumRepository
import com.biblublab.domain.common.EMPTY_LIST_IN_DB
import com.biblublab.domain.common.Either
import com.biblublab.domain.common.UNKNOWN_ERROR
import com.biblublab.domain.model.Album
import com.biblublab.domain.model.AlbumPhoto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

/**
 * this Class is responsible to manage access database and remote call
 * the database serves as the single source of truth
 *
 */
class AlbumRepositoryImpl(
    private val albumDao: AlbumDao,
    private val albumApi: AlbumApi,
    private val albumMapper: AlbumMapper
) : AlbumRepository {

    override suspend fun fetchData(): Either<String, Boolean> {
        var result: Either<String, Boolean> = Either.Failure(UNKNOWN_ERROR)
        coroutineScope {
            val albumsDeferred = async { safeApiCall { albumApi.getAlbums() } }
            val authorDeferred = async { safeApiCall { albumApi.getAuthors() } }
            val albumsResult = albumsDeferred.await()
            val authorResult = authorDeferred.await()

            result = if (albumsResult is Either.Successful && authorResult is Either.Successful) {
                albumDao.insertAuthors(authorResult.success.map { albumMapper.toAuthorDataBaseObject(it)})
                albumDao.insertAlbums(albumsResult.success.map { albumMapper.toAlbumDatabaseObject(it)})
                Either.Successful(true)
            } else {
                Either.Failure((albumsResult as Either.Failure).fail)
            }
        }
        return result
    }

    override fun fetchAlbums(): Flow<Either<String,List<Album>>> = albumDao.getAllAlbumWithAuthors().map { Either.Successful(it) }

    override fun fetchPhotos(albumId: Int): Flow<Either<String, List<AlbumPhoto>>> = flow {
        coroutineScope {
            val sourceDb: Flow<Either<String, List<AlbumPhoto>>> = albumDao.getPhotosFromAlbum(albumId).map {
                    if (it.isEmpty()) Either.Failure(EMPTY_LIST_IN_DB) else Either.Successful(it.map { photoDb -> albumMapper.toPhotoAlbumFromDb(photoDb)})}

            when (val networkCall = safeApiCall { albumApi.getPhotosByAlbum(albumId) }) {
                is Either.Successful -> albumDao.insertPhotos(networkCall.success.map { albumMapper.toPhotoDataBaseObject(it)})
                is Either.Failure -> emit(Either.Failure(networkCall.fail))
            }
            emitAll(sourceDb)
        }
    }

    private suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Either<String, T> {
        try {
            val response = call()
            if (response.isSuccessful && response.body() != null) {
                return Either.Successful(response.body()!!)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Either<String, T> =
        Either.Failure("Network call has failed because : $message")


}