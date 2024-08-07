import Wall.Attachments.*
import Wall.Exceptions.*
import Wall.Post.*
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class PostWallServiceTest {
    @Before
    fun clearBeforeTest() {
        PostWallService.clear()
    }

    @Test
    fun addPostTest() {
        val post = Post(
            attachments = listOf(
                PhotoAttachment(Photo(id = 1, ownerId = 1, date = 1206, userId = 1)),
                VideoAttachment(Video(id = 1, ownerId = 1, duration = 323, title = "TestVideo")),
                FileAttachment(File(id = 1, ownerId = 1, size = 100, title = "TestFile")),
                AudioAttachment(Audio(id = 1, ownerId = 1, artist = "TestArtist", title = "TestAlbum")),
                HistoryAttachment(History(id = 1, title = "TestHistory", isExpired = false, expiresAt = 123))
            )
        )
        val addedPost = PostWallService.add(post)

        assertEquals(addedPost.id, 1)
    }

    @Test
    fun updateExistingPostTrue() {
        val post = Post(
            attachments = listOf(
                PhotoAttachment(Photo(id = 1, ownerId = 1, date = 1206, userId = 1)),
                VideoAttachment(Video(id = 1, ownerId = 1, duration = 323, title = "TestVideo")),
                FileAttachment(File(id = 1, ownerId = 1, size = 100, title = "TestFile")),
                AudioAttachment(Audio(id = 1, ownerId = 1, artist = "TestArtist", title = "TestAlbum")),
                HistoryAttachment(History(id = 1, title = "TestHistory", isExpired = false, expiresAt = 123))
            )
        )

        PostWallService.add(post)
        val addedPost = post.copy(authorName = "Test name")

        assertEquals(PostWallService.update(addedPost), true)
    }

    @Test
    fun updateExistingPostFalse() {
        val post = Post(
            attachments = listOf(
                PhotoAttachment(Photo(id = 1, ownerId = 1, date = 1206, userId = 1)),
                VideoAttachment(Video(id = 1, ownerId = 1, duration = 323, title = "TestVideo")),
                FileAttachment(File(id = 1, ownerId = 1, size = 100, title = "TestFile")),
                AudioAttachment(Audio(id = 1, ownerId = 1, artist = "TestArtist", title = "TestAlbum")),
                HistoryAttachment(History(id = 1, title = "TestHistory", isExpired = false, expiresAt = 123))
            )
        )

        val addedPost = post.copy(authorName = "Test name")

        assertEquals(PostWallService.update(addedPost), false)
    }

    @Test(expected = PostNotFoundException::class)
    fun addCommentToNotExistingPost() {
        val post = Post(
            attachments = listOf(
                PhotoAttachment(Photo(id = 1, ownerId = 1, date = 1206, userId = 1)),
                VideoAttachment(Video(id = 1, ownerId = 1, duration = 323, title = "TestVideo")),
                FileAttachment(File(id = 1, ownerId = 1, size = 100, title = "TestFile")),
                AudioAttachment(Audio(id = 1, ownerId = 1, artist = "TestArtist", title = "TestAlbum")),
                HistoryAttachment(History(id = 1, title = "TestHistory", isExpired = false, expiresAt = 123))
            )
        )

        val id = 3
        val newComment = Comment(
            id = 1,
            can_post = true,
            groups_can_post = false,
            can_close = false,
            can_open = false
        )
        PostWallService.add(post)
        PostWallService.createComment(id, newComment)
    }

    @Test
    fun addCommentToExistingPost() {
        val post = Post(
            attachments = listOf(
                PhotoAttachment(Photo(id = 1, ownerId = 1, date = 1206, userId = 1)),
                VideoAttachment(Video(id = 1, ownerId = 1, duration = 323, title = "TestVideo")),
                FileAttachment(File(id = 1, ownerId = 1, size = 100, title = "TestFile")),
                AudioAttachment(Audio(id = 1, ownerId = 1, artist = "TestArtist", title = "TestAlbum")),
                HistoryAttachment(History(id = 1, title = "TestHistory", isExpired = false, expiresAt = 123))
            )
        )

        val id = 1
        val newComment = Comment(
            id = 1,
            can_post = true,
            groups_can_post = false,
            can_close = false,
            can_open = false
        )
        PostWallService.add(post)
        PostWallService.createComment(id, newComment)
    }

    @Test
    fun notValidReportByPost() {
        val post = Post(
            attachments = listOf(
                PhotoAttachment(Photo(id = 1, ownerId = 1, date = 1206, userId = 1)),
                VideoAttachment(Video(id = 1, ownerId = 1, duration = 323, title = "TestVideo")),
                FileAttachment(File(id = 1, ownerId = 1, size = 100, title = "TestFile")),
                AudioAttachment(Audio(id = 1, ownerId = 1, artist = "TestArtist", title = "TestAlbum")),
                HistoryAttachment(History(id = 1, title = "TestHistory", isExpired = false, expiresAt = 123))
            )
        )

        val postId = 1
        val newComment = Comment(
            id = 1,
            can_post = true,
            groups_can_post = false,
            can_close = false,
            can_open = false
        )
        PostWallService.add(post)
        PostWallService.createComment(postId, newComment)
        PostWallService.reportComment(2, newComment, 8)

        assertEquals(PostWallService.reportComment(2, newComment, 8), false)
    }

    @Test
    fun notValidReportByReason() {
        val post = Post(
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
            groups_can_post = false,
            can_close = false,
            can_open = false
        )
        PostWallService.add(post)
        PostWallService.createComment(commentId, newComment)

        assertEquals(PostWallService.reportComment(postId, newComment, 9), false)
    }

    @Test
    fun notValidReportByComment() {
        val post = Post(
            attachments = listOf(
                PhotoAttachment(Photo(id = 1, ownerId = 1, date = 1206, userId = 1)),
                VideoAttachment(Video(id = 1, ownerId = 1, duration = 323, title = "TestVideo")),
                FileAttachment(File(id = 1, ownerId = 1, size = 100, title = "TestFile")),
                AudioAttachment(Audio(id = 1, ownerId = 1, artist = "TestArtist", title = "TestAlbum")),
                HistoryAttachment(History(id = 1, title = "TestHistory", isExpired = false, expiresAt = 123))
            )
        )

        val commentId = 2
        val newComment = Comment(
            id = 1,
            can_post = true,
            groups_can_post = false,
            can_close = false,
            can_open = false
        )
        PostWallService.add(post)

        assertEquals(PostWallService.reportComment(commentId, newComment, 8), false)
    }

    @Test
    fun validReportByComment() {
        val post = Post(
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
            groups_can_post = false,
            can_close = false,
            can_open = false
        )
        PostWallService.add(post)
        PostWallService.createComment(postId, newComment)
        assertEquals(PostWallService.reportComment(commentId, newComment, 8), true)
    }
}