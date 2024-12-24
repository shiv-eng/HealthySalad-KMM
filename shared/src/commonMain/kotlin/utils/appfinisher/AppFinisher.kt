
package utils.appfinisher

import androidx.compose.runtime.compositionLocalOf


interface AppFinisher {

    fun finish()
}

val LocalAppFinisher = compositionLocalOf<AppFinisher> { error("Implementation not provided") }
