
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import navigation.NavGraph
import ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Surface {
            NavGraph()
        }
    }
}
