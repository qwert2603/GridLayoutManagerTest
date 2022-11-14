package com.example.testapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PhotoPickerAdapter(
    private val onPhotoClicked: (String, Photo) -> Unit,
    private val onPhotoLongClicked: (String, Photo) -> Unit,
) : ListAdapter<PhotoPicker, PhotoPickerAdapter.VH>(
    object : DiffUtil.ItemCallback<PhotoPicker>() {

        override fun areItemsTheSame(oldItem: PhotoPicker, newItem: PhotoPicker): Boolean = oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PhotoPicker, newItem: PhotoPicker): Boolean = oldItem == newItem

        override fun getChangePayload(oldItem: PhotoPicker, newItem: PhotoPicker) = Unit
    },
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo_picker, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val photoPicker = currentList[position]

        holder.adapter.submitList(photoPicker.photos) {
            holder.photosList.children.forEach(View::requestLayout) // THIS
        }

        holder.adapter.onPhotoClicked = { onPhotoClicked(photoPicker.id, it) }
        holder.adapter.onPhotoLongClicked = { onPhotoLongClicked(photoPicker.id, it) }
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photosList = itemView.findViewById<RecyclerView>(R.id.photos_list)
        val adapter = PhotoListAdapter()

        init {
            with(photosList) {
                layoutManager = GridLayoutManager(itemView.context, 2)
                adapter = this@VH.adapter
                itemAnimator = null
                isNestedScrollingEnabled = false
                isHorizontalScrollBarEnabled = false
                isVerticalScrollBarEnabled = false
                overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }
        }
    }
}
