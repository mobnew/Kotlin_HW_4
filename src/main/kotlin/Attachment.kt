package ru.netology

interface Attachment {
    val type: String
}

data class Photo(val id: Int, val ownerId: Int, val photo_130: String, val photo_604: String)
class PhotoAttachment(val photo: Photo): Attachment {
    override val type: String = "photo"
}

data class Video(val id: Int, val ownerId: Int, val title: String, val duration: Int)
class VideoAttachment(video: Video): Attachment {
    override val type: String = "video"
}

data class Audio(val id: Int, val ownerId: Int, val song: String, val duration: Int)
class  AudioAttachment(audio: Audio): Attachment {
    override val type: String
        get() = "audio"
}

data class File(val id: Int, val ownerId: Int, val fileName: String, val size: Int)
class FileAttachment(file: File): Attachment {
    override val type: String
        get() = "file"
}

data class Link(val id : Int, val ownerId: Int, val url: String, val title: String)
class LinkAttachment(link: Link): Attachment {
    override val type: String
        get() = "link"
}
