package ui.screen.detail

import androidx.compose.runtime.Immutable
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel

@GenerateMutableModel
@Immutable
interface PostDetailState {
    val isLoading: Boolean
    val post: Post?
    val errorMessage: String?

    @Immutable
    data class Post(val title: String, val content: String, val imageUrl: String?)
}
