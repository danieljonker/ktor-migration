package nz.co.jonker.ktorexample.posts

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import nz.co.jonker.ktorexample.BASE_URL

class KtorPostsRepo(private val httpClient: HttpClient) : PostsRepo {

    override suspend fun getPosts(): List<Post> {
        return httpClient.get {
            url("$BASE_URL/posts")
        }
    }
}

fun <T : HttpClientEngineConfig> provideHttpClient(
    engineFactory: HttpClientEngineFactory<T>,
    block: HttpClientConfig<T>.() -> Unit = {}
): HttpClient {
    return HttpClient(engineFactory) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        block()
    }
}
