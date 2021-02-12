package com.biblublab.domain.usecase

import com.biblublab.domain.AlbumRepository
import com.biblublab.domain.common.AppCoroutineDispatchers
import com.biblublab.domain.common.Either
import com.biblublab.domain.common.FlowUseCase
import com.biblublab.domain.model.AlbumPhoto
import kotlinx.coroutines.flow.Flow

class ObservePhotoUseCase(private val albumRepository: AlbumRepository, private val appCoroutineDispatchers: AppCoroutineDispatchers) : FlowUseCase<Int, String, List<AlbumPhoto>>(appCoroutineDispatchers.io) {

    override fun run(params: Int): Flow<Either<String, List<AlbumPhoto>>> {
        return albumRepository.fetchPhotos(params)
    }
}