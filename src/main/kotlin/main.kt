data class Post(
    val id: Int,
    val authorId: Int,
    val authorName: String,
    val content: String,
    val published: Long,
    val likes: Likes,
    val comments: Comments
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
        published = 2019)
    println(WallService.add(post))
}*/