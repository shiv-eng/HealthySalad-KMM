
package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import utils.accompanist.placeholder.PlaceholderHighlight
import utils.accompanist.placeholder.placeholder
import utils.accompanist.placeholder.shimmer
import utils.asyncimage.AsyncImage


@Composable
fun PostGraphicImage(url: String?, modifier: Modifier) {
    AsyncImage(
        imageUrl = url,
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.Crop,
        loadingPlaceholder = {
            Box(
                Modifier.matchParentSize()
                    .placeholder(true, highlight = PlaceholderHighlight.shimmer()),
            )
        },
        errorPlaceholder = {
            Box(Modifier.matchParentSize().background(Color.DarkGray)) {
                Text(
                    text = "X",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 32.sp,
                    color = Color.White,
                )
            }
        },
    )
}
