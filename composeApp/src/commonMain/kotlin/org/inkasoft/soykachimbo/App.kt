package org.inkasoft.soykachimbo

import androidx.compose.runtime.*

import coil3.ImageLoader
import org.jetbrains.compose.ui.tooling.preview.Preview
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import org.inkasoft.soykachimbo.data.database.QuestionsDao
import org.inkasoft.soykachimbo.ui.screens.Navigation
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory{ context ->
        ImageLoader.Builder(context)
            .crossfade(enable = true)
            .logger(DebugLogger())
            .build()
    }
    KoinContext {
        Navigation()
    }

}