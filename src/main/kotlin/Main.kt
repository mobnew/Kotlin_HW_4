package ru.netology

import kotlin.random.Random

data class Likes(
    val count: Int,
    val userLikes: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean
)

data class Reposts(
    val count: Int,
    val userReposted: Boolean
)

data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val text: String,
    val friendsOnly: Boolean? = false,
    val comments: Comment = Comment(0, 12,0,"true,true"),
    val likes: Likes = Likes(0, false, true, true),
    val reposts: Reposts = Reposts(0, false),
    val views: Int = 0,
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val attachment: List<Attachment> = emptyList()
)

data class Comment(
    val id: Int,
    val fromId: Int,
    val date: Int,
    val text: String
)

class PostNotFoundException(message: String): RuntimeException(message)

object WallService {
    private var arrayPosts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()

    @Throws(PostNotFoundException::class)
    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in arrayPosts) {
            if (post.id == postId) {
                comments += comment
                return comments.last()
            }
        }
        throw PostNotFoundException("Post with id $postId not found")
    }

    fun add(post: Post): Post {
        val tmpPost = post.copy(id = arrayPosts.count() + 1, ownerId = Random.nextInt(1,100_000))
        arrayPosts += tmpPost
        return arrayPosts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, postArr) in arrayPosts.withIndex()) {
            if (post.id == postArr.id) {
                arrayPosts[index] = post
                return true
            }
        }
        return false
    }

    fun clearArray() {
        arrayPosts = emptyArray()
    }
}