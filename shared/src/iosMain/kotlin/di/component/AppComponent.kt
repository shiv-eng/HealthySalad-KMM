
package di.component

import di.SqlDriverFactory

actual class AppContext

actual fun provideDriverFactory(appContext: AppContext): SqlDriverFactory = SqlDriverFactory()
