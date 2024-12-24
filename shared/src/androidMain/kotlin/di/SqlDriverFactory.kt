package di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.shivangi.healthysalad.db.SaladDb

actual class SqlDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(SaladDb.Schema, context, "salad.db")
    }
}
