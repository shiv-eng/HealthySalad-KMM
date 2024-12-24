
package ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ErrorContent(errorMessage: String) {
    Image(painterResource("icon_empty.xml"), contentDescription = null)
    Spacer(Modifier.height(16.dp))
    Text(
        "Something went wrong!",
        color = Color.Red,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
    )
    Spacer(Modifier.height(8.dp))
    Text(
        errorMessage,
        color = Color.Black,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}
