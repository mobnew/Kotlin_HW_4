import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.netology.Post
import ru.netology.WallService

class WallServiceTest {

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
        WallService.add(Post(text = "test post 1"))
        WallService.add(Post(text = "test post zzzzzzzzzz"))
        WallService.add(Post(text = "test post ppppppppppp"))

        val postForEdit = Post(id = 2, ownerId = 111, text = "changed text", friendsOnly = true, canPin = false, canDelete = false)
        val updatePost = WallService.update(postForEdit)

        assertTrue(updatePost)
    }

    @Test
    fun updateTestNegative() {
        WallService.add(Post(text = "test post 1"))
        WallService.add(Post(text = "test post zzzzzzzzzz"))
        WallService.add(Post(text = "test post ppppppppppp"))

        val postForEdit = Post(id = 20, ownerId = 111, text = "changed text", friendsOnly = true, canPin = false, canDelete = false)
        val updatePost = WallService.update(postForEdit)

        assertFalse(updatePost)
    }
}