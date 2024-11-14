package ru.netology

import kotlin.random.Random

data class Comments(
    val count: Int,
    val canPost: Boolean,
    val canGroupPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean
)

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
    val comments: Comments = Comments(0, true,true,true,true),
    val likes: Likes = Likes(0, false, true, true),
    val reposts: Reposts = Reposts(0, false),
    val views: Int = 0,
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val attachment: List<Attachment> = emptyList()
)

object WallService {
    var arrayPosts = emptyArray<Post>()

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