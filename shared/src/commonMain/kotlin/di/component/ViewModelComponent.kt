
package di.component

import kotlinx.coroutines.CoroutineScope
import ui.screen.detail.PostDetailViewModel
import ui.screen.home.HomeViewModel

interface ViewModelComponent {
    fun provideHomeViewModel(coroutineScope: CoroutineScope): HomeViewModel
    fun providePostDetailViewModel(coroutineScope: CoroutineScope, postId: Int): PostDetailViewModel
}

internal class DefaultViewModelComponent(
    private val appComponent: DefaultAppComponent,
) : ViewModelComponent {
    override fun provideHomeViewModel(coroutineScope: CoroutineScope): HomeViewModel {
        return HomeViewModel(coroutineScope, appComponent.postRepository)
    }

    override fun providePostDetailViewModel(
        coroutineScope: CoroutineScope,
        postId: Int,
    ): PostDetailViewModel {
        return PostDetailViewModel(
            viewModelScope = coroutineScope,
            postId = postId,
            repository = appComponent.postRepository,
        )
    }
}
