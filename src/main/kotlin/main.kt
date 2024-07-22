sealed class Attachment(val type: String)

data class Post(
    val id: Int,
    val authorId: Int,
    val authorName: String,
    val content: String,
    val published: Long,
    val likes: Likes?,
    val comments: Comments?,
    val attachments: List<Attachment>?
)

data class Likes(
    val count: Long,
    val user_likes: Boolean,
    val can_like: Boolean,
    val can_publish: Boolean
)

data class Comments(
    val count: Long,
    val can_post: Boolean,
    val gropus_can_post: Boolean,
    val can_close: Boolean,
    val can_open: Boolean
)

data class Photo(
    val id: Int,
    val userId: Int,
    val ownerId: Int,
    val date: Long
)

data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int
)

data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String
)

data class File(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val size: Int
)

data class History(
    val id: Int,
    val title: String,
    val expiresAt: Long,
    val isExpired: Boolean
)

data class VideoAttachment(val video: Video): Attachment("Video")

data class PhotoAttachment(val photo: Photo): Attachment("Photo")

data class AudioAttachment(val audio: Audio): Attachment("Audio")

data class FileAttachment(val file: File): Attachment("File")

data class HistoryAttachment(val history: History): Attachment("History")

object WallService {
    private var posts = emptyArray<Post>()
    private var nextPostId = 1

    fun add(post: Post): Post {
        val newPost = post.copy(id = nextPostId++)
        posts += newPost
        return newPost
    }

    fun update(post: Post): Boolean {
        for ((index, tempPost) in posts.withIndex()) {
            if (tempPost.id == post.id) {
                posts[index] = post.copy()
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray<Post>()
        nextPostId = 1
    }
}
/*fun main() {
    val post = Post(
        id = 1,
        authorId = 2,
        authorName = "John Doe",
        content = "Test",
        comments = Comments(
            count = 1,
            can_post = true,
            gropus_can_post = false,
            can_close = false,
            can_open = false),
        likes = Likes(
            count = 1,
            user_likes = true,
            can_like = true,
            can_publish = true
        ),
        published = 2019,
        attachments = listOf(
                PhotoAttachment(Photo(id = 1, ownerId = 1, date = 1206, userId = 1)),
                VideoAttachment(Video(id = 1, ownerId = 1, duration = 323, title = "TestVideo")),
                FileAttachment(File(id = 1, ownerId = 1, size = 100, title = "TestFile")),
                AudioAttachment(Audio(id = 1, ownerId = 1, artist = "TestArtist", title = "TestAlbum")),
                HistoryAttachment(History(id = 1, title = "TestHistory", isExpired = false, expiresAt = 123))))
    println(WallService.add(post))
}*/