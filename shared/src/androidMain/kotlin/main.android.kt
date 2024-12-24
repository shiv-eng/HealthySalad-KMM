
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import utils.appfinisher.AppFinisher
import utils.appfinisher.LocalAppFinisher

@Composable
fun MainView() {
    CompositionLocalProvider(LocalAppFinisher provides androidAppFinisher()) {
        App()
    }
}

@Composable
fun androidAppFinisher(): AppFinisher {
    val activity = LocalContext.current.findActivity()
    return object : AppFinisher {
        override fun finish() {
            activity.finish()
        }
    }
}

private fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Unable to retrieve Activity from the current context")
}