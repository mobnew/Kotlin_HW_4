package ru.netology

interface Attachment {
    val type: String
}

data class Photo(val id: Int, val ownerId: Int, val photo_130: String, val photo_604: String)
class PhotoAttachment(val photo: Photo): Attachment {
    override val type: String = "photo"
}