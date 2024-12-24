
package data.datasource.remote

import data.model.Post
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class PostRemoteDataSource(private val httpClient: HttpClient) {
    suspend fun getAllPosts(): List<Post> {
        return httpClient.get(GET_ALL_POSTS_URL).bodyAsText().let { json ->
            Json.decodeFromString(json)
        }
    }

    companion object {
        private const val BASE_URL = "https://shiv-eng.github.io/salads"
        private const val GET_ALL_POSTS_URL = "$BASE_URL/api"
    }
}
