package org.inkasoft.soykachimbo

import androidx.compose.ui.window.ComposeUIViewController
import org.inkasoft.soykachimbo.data.database.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }

) {
    App()
}