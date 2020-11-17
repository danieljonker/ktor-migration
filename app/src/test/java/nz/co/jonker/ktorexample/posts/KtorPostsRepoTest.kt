package nz.co.jonker.ktorexample.posts

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import junit.framework.TestCase
import kotlinx.coroutines.test.runBlockingTest

class KtorPostsRepoTest : TestCase() {

    private val client = provideHttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    "/posts" -> {
                        val headers = headersOf("Content-Type" to listOf("application/json"))
                        respond(json, headers = headers)
                    }
                    else -> error("Unhandled ${request.url.fullPath}")
                }
            }
        }
    }

    private val repo = KtorPostsRepo(client)

    fun `test ktor repo posts request`() = runBlockingTest {
        val expectedData = listOf(Post(1, 1, "Title 1", "Body 1"), Post(1, 2, "Title 2", "Body 2"))

        val posts = repo.getPosts()
        assertEquals(expectedData, posts)
    }
}

private val json: String = """
    [
      {
        "userId": 1,
        "id": 1,
        "title": "Title 1",
        "body": "Body 1"
      },
      {
        "userId": 1,
        "id": 2,
        "title": "Title 2",
        "body": "Body 2"
      }
  ]
""".trimIndent()