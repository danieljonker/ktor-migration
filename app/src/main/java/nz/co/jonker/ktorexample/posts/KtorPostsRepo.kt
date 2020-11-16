package nz.co.jonker.ktorexample.posts

import io.ktor.client.*
import io.ktor.client.request.*
import nz.co.jonker.ktorexample.BASE_URL

class KtorPostsRepo(private val httpClient: HttpClient) : PostsRepo {

    override suspend fun getPosts(): List<Post> {
        return httpClient.get {
            url("$BASE_URL/posts")
        }
    }
}
