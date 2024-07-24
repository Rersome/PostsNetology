import WallService.createComment

sealed class Attachment(val type: String)

data class Post(
    val id: Int,
    val authorId: Int,
    val authorName: String,
    val content: String,
    val published: Long,
    val likes: Likes?,
    val comments: Comment?,
    val attachments: List<Attachment>?
)

data class Likes(
    val count: Long,
    val user_likes: Boolean,
    val can_like: Boolean,
    val can_publish: Boolean
)

data class Report(
    val postId: Int,
    val comment: Comment?,
    val reason: Int,
)

data class Comment(
    val id: Int,
    val can_post: Boolean,
    val gropus_can_post: Boolean,
    val can_close: Boolean,
    val can_open: Boolean
)

data class Photo(
    val id: Long,
    val userId: Int,
    val ownerId: Int,
    val date: Long
)

data class Video(
    val id: Long,
    val ownerId: Int,
    val title: String,
    val duration: Int
)

data class Audio(
    val id: Long,
    val ownerId: Int,
    val artist: String,
    val title: String
)

data class File(
    val id: Long,
    val ownerId: Int,
    val title: String,
    val size: Int
)

data class History(
    val id: Long,
    val title: String,
    val expiresAt: Long,
    val isExpired: Boolean
)

data class VideoAttachment(val video: Video): Attachment("Video")

data class PhotoAttachment(val photo: Photo): Attachment("Photo")

data class AudioAttachment(val audio: Audio): Attachment("Audio")

data class FileAttachment(val file: File): Attachment("File")

data class HistoryAttachment(val history: History): Attachment("History")

class PostNotFoundException(message: String) : Exception(message)

class CommentNotFoundException(message: String) : Exception(message)

object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reports = emptyArray<Report>()
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
    // Реализация очень странная, я так думаю, но ничего умнее не пришло в голову (ДОРАБОТАТЬ)
    fun createComment(postId: Int, comment: Comment): Comment {
        for (tempPost in posts) {
            if (tempPost.id != postId) {
                throw PostNotFoundException("Поста с айди $postId не существует")
            } else {
                val newComment = comment.copy(id = nextPostId++)
                comments += newComment
            }
        }
        return comment
    }

    fun reportComment(postId: Int, comment: Comment, reason: Int): Boolean {
        if (reason !in 0..8) {
            return false
        }
        for (tempPost in posts) {
            if (tempPost.id == postId) {
                if (tempPost.comments != null && tempPost.comments.id == comment.id) {
                    val newReport = Report(postId, comment, reason)
                    reports += newReport
                    return true
                } else {
                    throw CommentNotFoundException("Комментария с айди ${comment.id} не существует в посте с айди $postId")
                }
            }
        }
        throw PostNotFoundException("Поста с айди $postId не существует")
    }

    fun clear() {
        posts = emptyArray<Post>()
        nextPostId = 1
    }
}
fun main() {
    /*val post = Post(
        id = 1,
        authorId = 2,
        authorName = "John Doe",
        content = "Test",
        comments = Comment(
            id = 1,
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

    val id = 1
    var newComment = Comment(
        id = 1,
        can_post = true,
        gropus_can_post = false,
        can_close = false,
        can_open = false
        )
    WallService.add(post)
    WallService.createComment(id, newComment)
    WallService.reportComment(1, newComment, 8)*/
}