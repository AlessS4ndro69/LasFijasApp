package org.inkasoft.soykachimbo.ui.screens.pdf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.ImageBitmap
import androidx.navigation.NavController
import kotlinx.cinterop.*


actual class PdfViewer {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun loadPdf(
        url: String
    ): List<ImageBitmap> {
        TODO("Gaaaaaa")
    }
}

@Composable
actual fun PdfViewerScreen(
    pdfUrl: String,
    modifier: Modifier,
    onBack: () -> Unit
) {
    TODO("Gaaaaaaaaaaaaaaa")
}
