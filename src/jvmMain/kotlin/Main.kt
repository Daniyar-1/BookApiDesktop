import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import screens.Home

@Composable
@Preview
fun App() {
    MaterialTheme() {
        val isOnProgress = remember { mutableStateOf(false) }
        Home(isOnProgress)
    }
}
fun main() = application {
    val iconApp = BitmapPainter(useResource("drawable/app_icon.png", ::loadImageBitmap))

    val icon = painterResource("drawable/app_icon.png")
    Tray(
        icon = icon,
        menu = {
            Item("BooksApp", onClick = ::exitApplication)
        })

    Window(title = "BookApp", onCloseRequest = ::exitApplication, icon = iconApp) {
        App()
    }
}