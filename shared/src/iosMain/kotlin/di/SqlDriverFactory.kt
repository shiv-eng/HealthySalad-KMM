
package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.shivangi.healthysalad.db.saladDb

actual class SqlDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(saladDb.Schema, "salad.db")
    }
}
