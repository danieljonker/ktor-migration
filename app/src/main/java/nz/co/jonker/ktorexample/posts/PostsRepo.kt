package nz.co.jonker.ktorexample.posts

import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import retrofit2.http.GET

class RetrofitPostsRepo(private val retrofit: Retrofit) : PostsRepo {
    private val postsHttpService = retrofit.create(PostsApi::class.java)

    override suspend fun getPosts(): List<Post> {
        return postsHttpService.getPosts()
    }
}

interface PostsRepo {
    suspend fun getPosts(): List<Post>
}

interface PostsApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}

@Serializable
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)