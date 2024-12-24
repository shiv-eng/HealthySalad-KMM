
package utils.backhandler

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // There's no need of handling system's back button in iOS platform
}
