package data.repository

import data.model.Post
import kotlinx.coroutines.flow.Flow


interface PostRepository {

    fun getAllPosts(): Flow<List<Post>>

    suspend fun findPostById(id: Int): Post?
}
