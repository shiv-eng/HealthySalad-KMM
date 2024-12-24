
package utils.accompanist.placeholder

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.roundToLong


@Stable
interface PlaceholderHighlight {

    val animationSpec: InfiniteRepeatableSpec<Float>?


    fun brush(
        progress: Float,
        size: Size,
    ): Brush


    fun alpha(progress: Float): Float

    companion object
}

fun PlaceholderHighlight.Companion.fade(
    highlightColor: Color,
    animationSpec: InfiniteRepeatableSpec<Float> = PlaceholderDefaults.fadeAnimationSpec,
): PlaceholderHighlight = Fade(
    highlightColor = highlightColor,
    animationSpec = animationSpec,
)

fun PlaceholderHighlight.Companion.shimmer(
    highlightColor: Color = Color.White,
    animationSpec: InfiniteRepeatableSpec<Float> = PlaceholderDefaults.shimmerAnimationSpec,
    progressForMaxAlpha: Float = 0.6f,
): PlaceholderHighlight = Shimmer(
    highlightColor = highlightColor,
    animationSpec = animationSpec,
    progressForMaxAlpha = progressForMaxAlpha,
)

private data class Fade(
    private val highlightColor: Color,
    override val animationSpec: InfiniteRepeatableSpec<Float>,
) : PlaceholderHighlight {
    private val brush = SolidColor(highlightColor)

    override fun brush(progress: Float, size: Size): Brush = brush
    override fun alpha(progress: Float): Float = progress
}

private data class Shimmer(
    private val highlightColor: Color,
    override val animationSpec: InfiniteRepeatableSpec<Float>,
    private val progressForMaxAlpha: Float = 0.6f,
) : PlaceholderHighlight {
    override fun brush(
        progress: Float,
        size: Size,
    ): Brush = Brush.radialGradient(
        colors = listOf(
            highlightColor.copy(alpha = 0f),
            highlightColor,
            highlightColor.copy(alpha = 0f),
        ),
        center = Offset(x = 0f, y = 0f),
        radius = (max(size.width, size.height) * progress * 2).coerceAtLeast(0.01f),
    )

    override fun alpha(progress: Float): Float = when {
        // From 0f...ProgressForOpaqueAlpha we animate from 0..1
        progress <= progressForMaxAlpha -> {
            lerp(
                start = 0f,
                stop = 1f,
                fraction = progress / progressForMaxAlpha,
            )
        }
        // From ProgressForOpaqueAlpha..1f we animate from 1..0
        else -> {
            lerp(
                start = 1f,
                stop = 0f,
                fraction = (progress - progressForMaxAlpha) / (1f - progressForMaxAlpha),
            )
        }
    }
}


fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

fun lerp(start: Int, stop: Int, fraction: Float): Int {
    return start + ((stop - start) * fraction.toDouble()).roundToInt()
}

fun lerp(start: Long, stop: Long, fraction: Float): Long {
    return start + ((stop - start) * fraction.toDouble()).roundToLong()
}
