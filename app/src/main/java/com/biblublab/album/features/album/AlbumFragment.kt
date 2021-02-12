package com.biblublab.album.features.album

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.biblublab.album.R
import com.biblublab.album.common.extension.*
import com.biblublab.album.common.mvi.BaseFragment
import com.biblublab.album.common.ui.UiState
import com.biblublab.album.databinding.AlbumFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class AlbumFragment : BaseFragment<AlbumScreenState, AlbumScreenEffect, AlbumScreenAction, AlbumViewModel>(R.layout.album_fragment) {

    private val binding by viewBinding(AlbumFragmentBinding::bind)
    override val viewModel: AlbumViewModel by viewModel()

    private val albumAdapter by lazy {
        AlbumAdapter { binding, albumEntity ->
            viewModel.process(AlbumScreenAction.OnClickItem(binding, albumEntity))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        waitForTransition(binding.albumListView)
    }

    override fun initUI() {
        binding.albumListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = albumAdapter
        }
    }

    override fun renderViewState(viewState: AlbumScreenState) {
        when (viewState.uiState) {
         is UiState.Error -> {
             binding.loadingShimmer.endShimmerAnim()
             activity?.showSnackbar(viewState.uiState.mess)
         }
         UiState.Fetching -> {
          binding.albumListView.visible(false)
          binding.loadingShimmer.beginShimmerAnim()
         }
         UiState.Result -> {
          binding.loadingShimmer.endShimmerAnim()
          binding.albumListView.visible(true)
         }
         UiState.NotFetched -> viewModel.process(AlbumScreenAction.FetchData)
            UiState.Fetched -> {
                activity?.showSnackbar(getString(R.string.data_updated))
                viewModel.process(AlbumScreenAction.DataFetched)
            }
        }
        albumAdapter.submitList(viewState.albumList)
    }

    override fun renderViewEffect(viewEffect: AlbumScreenEffect) {
        when(viewEffect){
            is AlbumScreenEffect.OpenGallery -> {
                val extras =  FragmentNavigatorExtras(viewEffect.binding.albumTitle to viewEffect.binding.albumTitle.transitionName)
                findNavController().navigate(AlbumFragmentDirections.openGalleryFragment(viewEffect.albumEntity), extras)
            }
        }
    }
}