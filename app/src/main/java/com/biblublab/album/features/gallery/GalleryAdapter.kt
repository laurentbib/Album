package com.biblublab.album.features.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.biblublab.album.common.extension.loadImg
import com.biblublab.album.databinding.GalleryItemViewBinding

class GalleryAdapter() : ListAdapter<PhotoEntity, GalleryAdapter.PhotoViewHolder>(
    PhotoItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = GalleryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PhotoViewHolder(private val binding : GalleryItemViewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(photoEntity: PhotoEntity){
            binding.Image.loadImg(photoEntity.photoUrl)
            binding.titleImage.text = photoEntity.photoTitle
        }
    }
}

internal  class PhotoItemDiffCallback : DiffUtil.ItemCallback<PhotoEntity>(){
    override fun areItemsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean = oldItem == newItem
}