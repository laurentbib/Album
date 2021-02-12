package com.biblublab.domain.usecase

import com.biblublab.domain.AlbumRepository
import com.biblublab.domain.common.AppCoroutineDispatchers
import com.biblublab.domain.common.Either
import com.biblublab.domain.common.FlowUseCase
import com.biblublab.domain.model.Album
import kotlinx.coroutines.flow.Flow

class ObserveAlbumsUseCase(private val albumRepository: AlbumRepository, private val appCoroutineDispatchers: AppCoroutineDispatchers) : FlowUseCase<Unit, String, List<Album>>(appCoroutineDispatchers.io) {

    override fun run(params: Unit): Flow<Either<String, List<Album>>> {
        return albumRepository.fetchAlbums()
    }
}