package ui.screen.home

import androidx.compose.runtime.Immutable
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel

@GenerateMutableModel
@Immutable
interface HomeState {
    val isLoading: Boolean
    val posts: List<Post>
    val errorMessage: String?

    @Immutable
    data class Post(val id: Int, val title: String, val imageUrl: String?)
}
