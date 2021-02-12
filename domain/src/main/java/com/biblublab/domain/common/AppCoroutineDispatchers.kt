package com.biblublab.domain.common

import kotlinx.coroutines.CoroutineDispatcher

class AppCoroutineDispatchers(val io: CoroutineDispatcher,
                              val computation: CoroutineDispatcher,
                              val main: CoroutineDispatcher)
