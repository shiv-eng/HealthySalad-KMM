
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import utils.appfinisher.AppFinisher
import utils.appfinisher.LocalAppFinisher

fun MainViewController() = ComposeUIViewController {
    CompositionLocalProvider(LocalAppFinisher provides iosAppFinisher()) {
        App()
    }
}

fun iosAppFinisher(): AppFinisher {

    return object : AppFinisher {
        override fun finish() {
            // no-op
        }
    }
}
