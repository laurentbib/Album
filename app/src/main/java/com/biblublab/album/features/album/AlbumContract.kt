package com.biblublab.album.features.album

import com.biblublab.album.common.ui.UiState
import com.biblublab.album.databinding.AlbumItemViewBinding

data class AlbumScreenState(val uiState: UiState, val albumList : List<AlbumEntity>)

sealed class AlbumScreenEffect{
    data class OpenGallery(val binding : AlbumItemViewBinding, val albumEntity: AlbumEntity) : AlbumScreenEffect()
}

sealed class AlbumScreenAction{
    object FetchData : AlbumScreenAction()
    object DataFetched : AlbumScreenAction()
    data class OnClickItem(val binding : AlbumItemViewBinding, val albumEntity: AlbumEntity) : AlbumScreenAction()

}