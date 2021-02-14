package com.biblublab.album.features.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.biblublab.album.R
import com.biblublab.album.databinding.AlbumItemViewBinding

class AlbumAdapter(private val onClickItem : (AlbumItemViewBinding, AlbumEntity) -> Unit) : ListAdapter<AlbumEntity, AlbumAdapter.AlbumViewHolder>(AlbumItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = AlbumItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AlbumViewHolder(private val binding : AlbumItemViewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(albumEntity: AlbumEntity){
            binding.albumTitle.transitionName = binding.root.resources.getString(R.string.transition_album_title, albumEntity.albumId)
            binding.albumTitle.text = albumEntity.title
            binding.albumAuthor.text = albumEntity.authorName
            binding.root.setOnClickListener{onClickItem(binding, albumEntity)}
        }
    }
}

internal  class AlbumItemDiffCallback : DiffUtil.ItemCallback<AlbumEntity>(){
    override fun areItemsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity): Boolean = oldItem == newItem
}