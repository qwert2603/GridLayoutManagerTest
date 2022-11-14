package com.example.testapp

data class State(
    val items: List<PhotoPicker>,
)

data class PhotoPicker(
    val id: String,
    val photos: List<Photo>,
)

data class Photo(
    val id: String,
    val isAddPhoto: Boolean,
    val showError: Boolean,
)