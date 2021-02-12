package com.biblublab.album.features.album

import androidx.lifecycle.viewModelScope
import com.biblublab.album.common.mvi.BaseViewModel
import com.biblublab.album.common.ui.UiState
import com.biblublab.domain.model.Album
import com.biblublab.domain.usecase.FetchDataUseCase
import com.biblublab.domain.usecase.ObserveAlbumsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AlbumViewModel(val fetchDataUseCase: FetchDataUseCase,
                     val observeAlbumsUseCase: ObserveAlbumsUseCase) : BaseViewModel<AlbumScreenState, AlbumScreenEffect, AlbumScreenAction>() {

    init {
       viewState = AlbumScreenState(UiState.NotFetched, albumList = emptyList())

        observeAlbumsUseCase.invoke(Unit).onEach{
            it.either({},
                {albumList ->
                    viewState = viewState.copy(uiState = if(albumList.isEmpty())UiState.Fetching else UiState.Result, albumList = albumList.map { album -> toAlbumEntity(album) })})
        }.launchIn(viewModelScope)
    }

    override fun process(viewAction: AlbumScreenAction) {
        super.process(viewAction)
        when(viewAction){
            AlbumScreenAction.FetchData -> fetchData()
            is AlbumScreenAction.OnClickItem -> viewEffect =
                AlbumScreenEffect.OpenGallery(viewAction.binding, viewAction.albumEntity)
            AlbumScreenAction.DataFetched ->  viewState = viewState.copy(uiState = UiState.Result)
        }
    }

    private fun fetchData(){
       // viewState = viewState.copy(uiState = UiState.Fetching)
        viewModelScope.launch {
            fetchDataUseCase().either({
                viewState = viewState.copy(uiState = UiState.Error(it))
            },{
                viewState = viewState.copy(uiState = UiState.Fetched)
            })
        }
    }

    private fun toAlbumEntity(album: Album) = with(album){
        AlbumEntity(title = title, authorName = "$name $username", albumId = albumId)
    }
}