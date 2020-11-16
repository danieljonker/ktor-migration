package nz.co.jonker.ktorexample.posts

import kotlinx.serialization.Serializable
import retrofit2.http.GET


interface PostsRepo {
    suspend fun getPosts(): List<Post>
}

@Serializable
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)