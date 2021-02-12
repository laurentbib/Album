package com.biblublab.domain.usecase

import com.biblublab.domain.AlbumRepository
import com.biblublab.domain.common.*

class FetchDataUseCase(private val albumRepository: AlbumRepository,
                       private val appCoroutineDispatchers: AppCoroutineDispatchers) : UseCase<Unit, String, Boolean>(appCoroutineDispatchers.io) {

    override suspend fun run(params: Unit?): Either<String, Boolean> {
        return albumRepository.fetchData()
    }


}