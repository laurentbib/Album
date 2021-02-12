package com.biblublab.album.common.ui

sealed class UiState {
    object NotFetched : UiState()
    object Fetching : UiState()
    object Fetched : UiState()
    object Result : UiState()
    data class Error(val mess : String) : UiState()
}