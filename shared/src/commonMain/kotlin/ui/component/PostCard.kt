package ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.screen.home.HomeState
import utils.accompanist.placeholder.PlaceholderHighlight
import utils.accompanist.placeholder.placeholder
import utils.accompanist.placeholder.shimmer

@Composable
fun PostCard(
    isLoading: Boolean,
    post: HomeState.Post,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            PostGraphicImage(
                url = post.imageUrl,
                modifier = Modifier
                    .size(100.dp)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f) // Take remaining space
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(
                            visible = isLoading,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Add more text or UI elements here if needed
            }
        }
    }
}
