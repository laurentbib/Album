package com.biblublab.domain.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in Params, out FailureType, out SuccessType>(private val coroutineDispatcher: CoroutineDispatcher)  {

    abstract  fun run(params: Params): Flow<Either<FailureType, SuccessType>>

    operator fun invoke(params: Params): Flow<Either<FailureType, SuccessType>>{
        return run(params)
            .distinctUntilChanged()
            .catch{ Either.Failure(UNKNOWN_ERROR)  }
            .flowOn(coroutineDispatcher)
    }
}