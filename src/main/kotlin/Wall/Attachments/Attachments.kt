package Wall.Attachments

data class Photo(
    val id: Long = 1,
    val userId: Int = 1,
    val ownerId: Int = 1,
    val date: Long = 1206
)

data class Video(
    val id: Long = 1,
    val ownerId: Int = 1,
    val title: String = "TestVideo",
    val duration: Int = 323
)

data class Audio(
    val id: Long = 1,
    val ownerId: Int = 1,
    val artist: String = "TestArtist",
    val title: String = "TestAlbum"
)

data class File(
    val id: Long = 1,
    val ownerId: Int = 1,
    val title: String = "TestFile",
    val size: Int = 100
)

data class History(
    val id: Long = 1,
    val title: String = "TestHistory",
    val expiresAt: Long = 123,
    val isExpired: Boolean = false
)

sealed class Attachment(val type: String)

data class VideoAttachment(val video: Video): Attachment("Video")

data class PhotoAttachment(val photo: Photo): Attachment("Photo")

data class AudioAttachment(val audio: Audio): Attachment("Audio")

data class FileAttachment(val file: File): Attachment("File")

data class HistoryAttachment(val history: History): Attachment("History")