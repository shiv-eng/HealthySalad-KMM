package ui.screen.detail

import base.ViewModel
import data.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(
    viewModelScope: CoroutineScope,
    postId: Int,
    private val repository: PostRepository,
) : ViewModel<PostDetailState>(viewModelScope) {

    private val _state = MutablePostDetailState(isLoading = true, post = null, errorMessage = null)
    override val state: StateFlow<PostDetailState> = _state.asStateFlow()

    init {
        loadPost(postId)
    }

    private fun loadPost(postId: Int) {
        viewModelScope.launch {
            _state.isLoading = true

            val post = repository.findPostById(postId)
            if (post != null) {
                _state.post = post.let {
                    PostDetailState.Post(
                        title = it.title,
                        content = it.body,
                        imageUrl = it.imageUrl,
                    )
                }
            } else {
                _state.errorMessage = "Can't find the post"
            }
            _state.isLoading = false
        }
    }
}
