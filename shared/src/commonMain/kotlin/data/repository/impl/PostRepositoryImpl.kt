package data.repository.impl

import data.datasource.cache.PostCacheDataSource
import data.datasource.remote.PostRemoteDataSource
import data.model.Post
import data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow


internal class PostRepositoryImpl(
    private val remoteDataSource: PostRemoteDataSource,
    private val cacheDataSource: PostCacheDataSource,
) : PostRepository {
    override fun getAllPosts(): Flow<List<Post>> {
        return flow {
            emit(cacheDataSource.getAllPosts().first())

            cacheDataSource.replaceAllPosts(remoteDataSource.getAllPosts())

            emitAll(cacheDataSource.getAllPosts())
        }
    }

    override suspend fun findPostById(id: Int): Post? {
        return cacheDataSource.findPostById(id)
    }
}
