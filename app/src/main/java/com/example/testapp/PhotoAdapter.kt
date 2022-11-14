package com.example.testapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PhotoListAdapter : ListAdapter<Photo, PhotoListAdapter.VH>(
    object : DiffUtil.ItemCallback<Photo>() {

        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem

        override fun getChangePayload(oldItem: Photo, newItem: Photo) = Unit
    },
) {
    var onPhotoClicked: (Photo) -> Unit = {}
    var onPhotoLongClicked: (Photo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val photo = currentList[position]
        holder.itemView.setOnClickListener { onPhotoClicked(photo) }
        holder.itemView.setOnLongClickListener { onPhotoLongClicked(photo);true }
        holder.photoView.setBackgroundColor(if (photo.isAddPhoto) Color.RED else Color.YELLOW)
        holder.errorTextView.isVisible = photo.showError
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoView = itemView.findViewById<View>(R.id.photo_view)
        val errorTextView = itemView.findViewById<TextView>(R.id.error_text_view)
    }
}


class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.VH>() {
    var onPhotoClicked: (Photo) -> Unit = {}
    var onPhotoLongClicked: (Photo) -> Unit = {}

    private var list: List<Photo> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Photo>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val photo = list[position]
        holder.itemView.setOnClickListener { onPhotoClicked(photo) }
        holder.itemView.setOnLongClickListener { onPhotoLongClicked(photo);true }
        holder.photoView.setBackgroundColor(if (photo.isAddPhoto) Color.RED else Color.YELLOW)
        holder.errorTextView.isVisible = photo.showError
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoView = itemView.findViewById<View>(R.id.photo_view)
        val errorTextView = itemView.findViewById<TextView>(R.id.error_text_view)
    }
}
