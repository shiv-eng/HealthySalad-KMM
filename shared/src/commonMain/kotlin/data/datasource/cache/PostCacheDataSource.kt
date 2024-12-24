
package data.datasource.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import data.model.Post
import com.shivangi.healthysalad.db.SaladDb
import com.shivangi.healthysalad.db.Posts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class PostCacheDataSource(
    private val db: SaladDb,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun replaceAllPosts(posts: List<Post>) = withContext(ioDispatcher) {
        db.postsQueries.transaction {
            db.postsQueries.run {
                deleteAll()
                posts.forEach {
                    addPost(
                        id = it.id.toLong(),
                        title = it.title,
                        body = it.body,
                        imageUrl = it.imageUrl,
                    )
                }
            }
        }
    }


    fun getAllPosts(): Flow<List<Post>> {
        return db.postsQueries
            .selectAll()
            .asFlow()
            .mapToList(ioDispatcher).map { posts -> posts.map { it.asPost() } }
    }


    suspend fun findPostById(id: Int): Post? = withContext(ioDispatcher) {
        db.postsQueries.findById(id.toLong()).executeAsOneOrNull()?.asPost()
    }

    private fun Posts.asPost() = Post(
        id = id.toInt(),
        title = title,
        body = body,
        imageUrl = imageUrl,
    )
}
