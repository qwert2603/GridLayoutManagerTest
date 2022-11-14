package com.example.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var items = listOf(
        PhotoPicker(
            id = "photo_picker_1",
            photos = listOf(
                Photo(
                    id = "add_1",
                    isAddPhoto = true,
                    showError = false,
                ),
            ),
        ),
        PhotoPicker(
            id = "photo_picker_2",
            photos = listOf(
                Photo(
                    id = "add_2",
                    isAddPhoto = true,
                    showError = false,
                ),
            ),
        ),
    )
        set(value) {
            field = value
            adapter.submitList(field)
        }


    private fun List<PhotoPicker>.mapPhotoPicker(id: String, map: (PhotoPicker) -> PhotoPicker) = map {
        if (it.id == id) {
            map(it)
        } else {
            it
        }
    }

    private fun List<Photo>.mapPhoto(id: String, map: (Photo) -> Photo) = map {
        if (it.id == id) {
            map(it)
        } else {
            it
        }
    }

    private fun PhotoPicker.addPhoto(showError: Boolean): PhotoPicker {
        val newPhoto = Photo(
            id = Random.nextLong().toString(),
            isAddPhoto = false,
            showError = showError,
        )
        return copy(photos = photos.dropLast(1) + newPhoto + photos.last())
    }

    private val adapter = PhotoPickerAdapter(
        onPhotoClicked = { photoPickerId: String, photo: Photo ->
            items = items.mapPhotoPicker(photoPickerId) { photoPicker ->
                if (photo.isAddPhoto) {
                    photoPicker.addPhoto(showError = false)
                } else {
                    photoPicker.copy(photos = photoPicker.photos.filter { it.id != photo.id })
                }
            }
        },
        onPhotoLongClicked = { photoPickerId: String, photo: Photo ->
            items = items.mapPhotoPicker(photoPickerId) { photoPicker ->
                if (photo.isAddPhoto) {
                    photoPicker.addPhoto(showError = true)
                } else {
                    photoPicker.copy(photos = photoPicker.photos.mapPhoto(photo.id) {
                        it.copy(showError = !it.showError)
                    })
                }
            }
        },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemsList = findViewById<RecyclerView>(R.id.items_list)
        itemsList.adapter = adapter
        itemsList.layoutManager = LinearLayoutManager(this)
        adapter.submitList(items)
    }
}