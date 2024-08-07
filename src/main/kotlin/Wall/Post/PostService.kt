package Wall.Post

import Wall.Attachments.Attachment
import Wall.Exceptions.PostNotFoundException

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
    val groups_can_post: Boolean = false,
    val can_close: Boolean = false,
    val can_open: Boolean = false
)

object PostWallService {
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
        for (tempPost in posts) {
            if (tempPost.id == postId) {
                val newComment = comment.copy(id = nextCommentId++)
                comments += newComment
                return comment
            }
        }
        throw PostNotFoundException("Поста с айди $postId не существует")
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