import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.netology.*

class WallServiceTest {

    private fun addPostsForTesting() {
        WallService.add(Post(text = "test post 1"))
        WallService.add(Post(text = "test post zzzzzzzzzz"))
        WallService.add(Post(text = "test post ppppppppppp"))
    }

    @Test
    fun addTest() {
        val post = WallService.add(Post(text = "test post 1", friendsOnly = null))
        assertNotEquals(0, post.id)
    }

    @Before
    fun clearBeforeTest() {
        WallService.clearArray()
    }

    @Test
    fun updateTestPositive() {
        addPostsForTesting()

        val postForEdit = Post(id = 2, ownerId = 111, text = "changed text", friendsOnly = true, canPin = false, canDelete = false)
        val updatePost = WallService.update(postForEdit)

        assertTrue(updatePost)
    }

    @Test
    fun updateTestNegative() {
        addPostsForTesting()

        val postForEdit = Post(id = 20, ownerId = 111, text = "changed text", friendsOnly = true, canPin = false, canDelete = false)
        val updatePost = WallService.update(postForEdit)

        assertFalse(updatePost)
    }

    @Test
    fun updateTestWithAttachments() {
        addPostsForTesting()

        val photo = Photo(1, 1, photo_130 = "https://vk.com/some_photo_link" , photo_604 = "https://vk.com/another_photo_link")
        val photoAttachment = PhotoAttachment(photo)

        val video = Video(1,1, title = "A Funny Video", duration = 30)
        val videoAttachment = VideoAttachment(video)

        val audio = Audio(1, 1, song = "La-La", duration = 180)
        val audioAttachment = AudioAttachment(audio)

        val file = File(1, 1, "dontTouch.docx", 1024)
        val fileAttachment= FileAttachment(file)

        val link = Link(1, 1, url = "https://ya.ru", title = "Yandex")
        val linkAttachment = LinkAttachment(link)

        val postForEdit = Post(id = 2, ownerId = 111, text = "changed text", attachment = listOf(photoAttachment, videoAttachment, audioAttachment, fileAttachment, linkAttachment))
        val updatePost = WallService.update(postForEdit)

        assertTrue(updatePost)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        addPostsForTesting()

        val id = 20
        val testComment = Comment(1,15,0,"first")
        WallService.createComment(id, testComment)
    }

    @Test
    fun shouldNotThrow() {
        addPostsForTesting()

        val id = 1
        val testComment = Comment(1,15,0,"first")
        WallService.createComment(id, testComment)
    }


}