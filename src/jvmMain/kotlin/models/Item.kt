package models

data class Item(
    val id: String,
    val volumeInfo: VolumeInfo,
    val previewLink: BookLinks?
)
