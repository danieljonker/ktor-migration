package nz.co.jonker.ktorexample.posts

import retrofit2.Retrofit
import retrofit2.http.GET

class RetrofitPostsRepo(retrofit: Retrofit) : PostsRepo {
    private val postsHttpService = retrofit.create(PostsApi::class.java)

    override suspend fun getPosts(): List<Post> {
        return postsHttpService.getPosts()
    }
}

interface PostsApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}