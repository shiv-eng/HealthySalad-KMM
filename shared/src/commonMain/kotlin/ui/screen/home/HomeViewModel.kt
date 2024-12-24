package ui.screen.home

import base.ViewModel
import data.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class HomeViewModel(
    coroutineScope: CoroutineScope,
    private val repository: PostRepository,
) : ViewModel<HomeState>(coroutineScope) {
    private val _state = MutableHomeState(
        posts = emptyList(),
        isLoading = true,
        errorMessage = null,
    )

    override val state = _state.asStateFlow()

    private var observePostsJob: Job? = null

    init {
        loadPosts()
    }

    fun refresh() {
        loadPosts()
    }

    private fun loadPosts() {
        observePostsJob?.cancel()
        observePostsJob = viewModelScope.launch {
            _state.isLoading = true

            repository.getAllPosts()
                .catch {
                    _state.update {
                        if (posts.isEmpty()) {
                            errorMessage = "Error occurred: ${it.message}"
                        }
                        isLoading = false
                    }
                }
                .collect { posts ->
                    val postsState = posts.map {
                        HomeState.Post(
                            id = it.id,
                            title = it.title,
                            imageUrl = it.imageUrl,
                        )
                    }
                    _state.update {
                        this.posts = postsState
                        this.isLoading = false
                    }
                }
        }
    }
}
