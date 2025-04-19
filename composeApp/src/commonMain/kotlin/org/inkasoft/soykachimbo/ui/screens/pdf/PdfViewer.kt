package org.inkasoft.soykachimbo.ui.screens.pdf

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.navigation.NavController

expect class PdfViewer {
    suspend fun loadPdf(
        url: String
    ): List<ImageBitmap>
}

@Composable
expect fun PdfViewerScreen(
    pdfUrl: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
)