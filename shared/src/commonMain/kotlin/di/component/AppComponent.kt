package di.component

import data.datasource.cache.PostCacheDataSource
import data.datasource.remote.PostRemoteDataSource
import data.repository.impl.PostRepositoryImpl
import com.shivangi.healthysalad.db.SaladDb
import di.SqlDriverFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import kotlin.jvm.Volatile

expect class AppContext


interface AppComponent {


    val viewModelComponent: ViewModelComponent

    companion object : AppComponent by Injector.get()

    object Injector {
        @Volatile
        private var instance: DefaultAppComponent? = null

        fun inject(appContext: AppContext) {
            if (instance == null) {
                instance = DefaultAppComponent(appContext)
            }
        }

        fun get(): AppComponent = instance ?: error("DI is not injected yet!")
    }
}


internal class DefaultAppComponent(private val appContext: AppContext) : AppComponent {

    val postRepository by lazy {
        PostRepositoryImpl(
            remoteDataSource = remoteDataSource,
            cacheDataSource = cacheDataSource,
        )
    }

    private val remoteDataSource by lazy { PostRemoteDataSource(provideHttpClient()) }
    private val cacheDataSource by lazy {
        PostCacheDataSource(
            db = saladDb,
            ioDispatcher = Dispatchers.IO,
        )
    }
    private val saladDb by lazy { SaladDb(provideDriverFactory(appContext).create()) }

    private fun provideHttpClient(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override val viewModelComponent: ViewModelComponent by lazy { DefaultViewModelComponent(this) }
}

expect fun provideDriverFactory(appContext: AppContext): SqlDriverFactory
