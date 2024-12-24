
package navigation

private val KEY_SCREEN_NAME = "screen_name"


fun <S : Screen> S.asSavable(): Map<String, String> {
    return when (this) {
        is Screen.PostDetail -> savable<Screen.PostDetail>("postId" to postId.toString())
        is Screen.Home -> savable<Screen.Home>()
        else -> error("Can't save state for screen: ${this@asSavable}. Reason: Undefined")
    }
}


fun buildScreenFromSavable(savable: Map<String, String>): Screen {
    return when (val screenName = savable[KEY_SCREEN_NAME]) {
        screenName<Screen.Home>() -> Screen.Home
        screenName<Screen.PostDetail>() -> Screen.PostDetail(savable["postId"]!!.toInt())
        else -> error("Can't restore state for screen: $screenName. Reason: Undefined")
    }
}


private inline fun <reified S : Screen> savable(
    vararg pairs: Pair<String, String>,
) = buildMap<String, String> {
    put(KEY_SCREEN_NAME, screenName<S>())
    putAll(pairs)
}

inline fun <reified SCREEN : Screen> screenName(): String = SCREEN::class.qualifiedName!!
