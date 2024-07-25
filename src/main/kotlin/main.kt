sealed class Attachment(val type: String)

data class Post(
    val id: Int = 1,
    val authorId: Int = 2,
    val authorName: String = "John Doe",
    val content: String = "Test",
    val published: Long = 2019,
    val likes: Likes? = Likes(),
    val comments: Comment? = Comment(),
    val attachments: List<Attachment>?
)

data class Likes(
    val count: Long = 1,
    val user_likes: Boolean = true,
    val can_like: Boolean = true,
    val can_publish: Boolean = true
)

data class Report(
    val postId: Int,
    val comment: Comment?,
    val reason: Int,
)

data class Comment(
    val id: Int = 1,
    val can_post: Boolean = true,
    val gropus_can_post: Boolean = false,
    val can_close: Boolean = false,
    val can_open: Boolean = false
)

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

data class VideoAttachment(val video: Video): Attachment("Video")

data class PhotoAttachment(val photo: Photo): Attachment("Photo")

data class AudioAttachment(val audio: Audio): Attachment("Audio")

data class FileAttachment(val file: File): Attachment("File")

data class HistoryAttachment(val history: History): Attachment("History")

class PostNotFoundException(message: String) : Exception(message)

object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reports = emptyArray<Report>()
    private var nextPostId = 1
    private var nextCommentId = 1

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
    // Реализация очень странная, я так думаю, но ничего умнее не пришло в голову (ДОРАБОТАТЬ)
    fun createComment(postId: Int, comment: Comment): Comment {
        var postFound = false
        for (tempPost in posts) {
            if (tempPost.id == postId) {
                postFound = true
                val newComment = comment.copy(id = nextCommentId++)
                comments += newComment
            } else throw PostNotFoundException("Поста с айди $postId не существует")
        }
        return comment
    }

    fun reportComment(postId: Int, comment: Comment, reason: Int): Boolean {
        if (reason !in 0..8) {
            return false
        }
        var postFound = false
        var commentFound = false

        for (tempPost in posts) {
            if (tempPost.id == postId) {
                postFound = true
                for (tempComment in comments) {
                    if (tempComment.id == comment.id) {
                        commentFound = true
                        val newReport = Report(postId, comment, reason)
                        reports += newReport
                        return true
                    }
                }
                break
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray<Post>()
        comments = emptyArray<Comment>()
        reports = emptyArray<Report>()
        nextPostId = 1
        nextCommentId = 1
    }
}
fun main() {
    /*val post = Post(
        attachments = listOf(
            PhotoAttachment(Photo(id = 1, ownerId = 1, date = 1206, userId = 1)),
            VideoAttachment(Video(id = 1, ownerId = 1, duration = 323, title = "TestVideo")),
            FileAttachment(File(id = 1, ownerId = 1, size = 100, title = "TestFile")),
            AudioAttachment(Audio(id = 1, ownerId = 1, artist = "TestArtist", title = "TestAlbum")),
            HistoryAttachment(History(id = 1, title = "TestHistory", isExpired = false, expiresAt = 123))
        )
    )

    val postId = 1
    val commentId = 1
    val newComment = Comment(
        id = 1,
        can_post = true,
        gropus_can_post = false,
        can_close = false,
        can_open = false
    )
    WallService.add(post)
    WallService.createComment(postId, newComment)
    WallService.reportComment(commentId, newComment, 8)*/
}