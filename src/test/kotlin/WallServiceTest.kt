import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class WallServiceTest {
    @Before
    fun cleareBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addPostTest() {
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
        val addedPost = WallService.add(post)

        assertEquals(addedPost.id, 1)
    }

    @Test
    fun updateExistingPostTrue() {
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

        WallService.add(post)
        val addedPost = post.copy(authorName = "Test name")

        assertEquals(WallService.update(addedPost), true)
    }

    @Test
    fun updateExistingPostFalse() {
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
                HistoryAttachment(History(id = 1, title = "TestHistory", isExpired = false, expiresAt = 123)))
        )

        val addedPost = post.copy(authorName = "Test name")

        assertEquals(WallService.update(addedPost), false)
    }
}