package com.biblublab.album.features.gallery

import com.biblublab.album.common.ui.UiState

data class GalleryScreenState(val uiState: UiState, val gallery : List<PhotoEntity>)

sealed class GalleryScreenEffect{
    object BackToAlbumScreen : GalleryScreenEffect()
}

sealed class GalleryScreenAction{
    data class FetchData(val albumId : Int) : GalleryScreenAction()

}