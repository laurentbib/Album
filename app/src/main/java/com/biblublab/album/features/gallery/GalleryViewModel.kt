package com.biblublab.album.features.gallery

import androidx.lifecycle.viewModelScope
import com.biblublab.album.common.mvi.BaseViewModel
import com.biblublab.album.common.ui.UiState
import com.biblublab.domain.model.AlbumPhoto
import com.biblublab.domain.usecase.ObservePhotoUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GalleryViewModel(private val observePhotoUseCase: ObservePhotoUseCase) : BaseViewModel<GalleryScreenState, GalleryScreenEffect, GalleryScreenAction>() {

    init {
        viewState = GalleryScreenState(uiState = UiState.NotFetched, gallery = emptyList())
    }

    override fun process(viewAction: GalleryScreenAction) {
        super.process(viewAction)
        when(viewAction){
            is GalleryScreenAction.FetchData -> fetchPhotos(viewAction.albumId)
        }
    }

    private fun fetchPhotos(albumId : Int) {
        viewState = viewState.copy(uiState = UiState.Fetching)
        observePhotoUseCase.invoke(albumId).onEach {
            it.either({ errorMessage ->
                viewState = viewState.copy(uiState = UiState.Error(errorMessage))
            }, { listPhoto ->
                viewState = viewState.copy(uiState = UiState.Result, gallery = listPhoto.map { albumPhoto ->  toPhotoEntity(albumPhoto) })
            })
        }.launchIn(viewModelScope)
    }

    private fun toPhotoEntity(albumPhoto: AlbumPhoto) = with(albumPhoto){
        PhotoEntity(photoId, photoUrl, photoTitle)
    }
}