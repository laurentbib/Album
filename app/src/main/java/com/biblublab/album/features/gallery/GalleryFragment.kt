package com.biblublab.album.features.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.biblublab.album.R
import com.biblublab.album.common.extension.*
import com.biblublab.album.common.mvi.BaseFragment
import com.biblublab.album.common.ui.UiState
import com.biblublab.album.databinding.GalleryFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class GalleryFragment :
    BaseFragment<GalleryScreenState, GalleryScreenEffect, GalleryScreenAction, GalleryViewModel>(R.layout.gallery_fragment) {

    private val binding by viewBinding(GalleryFragmentBinding::bind)
    override val viewModel: GalleryViewModel by viewModel()

    private val args: GalleryFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sharedElementEnterTransition = TransitionInflater.from(this.context).inflateTransition(R.transition.change_bounds)
        sharedElementReturnTransition =  TransitionInflater.from(this.context).inflateTransition(R.transition.change_bounds)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        waitForTransition(binding.albumTitle)
        binding.albumTitle.transitionName = getString(R.string.transition_album_title, args.albumEntity.albumId)
    }

    private val galleryAdapter by lazy {
        GalleryAdapter()
    }

    override fun initUI() {
        binding.galleryListView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = galleryAdapter
        }
        binding.albumTitle.text = args.albumEntity.title
    }

    override fun renderViewState(viewState: GalleryScreenState) {
        when (viewState.uiState) {
            is UiState.Error -> {
                binding.loadingShimmer.endShimmerAnim()
                activity?.showSnackbar(viewState.uiState.mess)
            }
            UiState.Fetching -> {
                binding.galleryListView.visible(false)
                binding.loadingShimmer.beginShimmerAnim()
            }
            UiState.NotFetched -> viewModel.process(GalleryScreenAction.FetchData(args.albumEntity.albumId))
            else -> {
                binding.loadingShimmer.endShimmerAnim()
                binding.galleryListView.visible(true)
            }
        }
        galleryAdapter.submitList(viewState.gallery)
    }

    override fun renderViewEffect(viewEffect: GalleryScreenEffect) {}


}