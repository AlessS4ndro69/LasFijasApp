    package org.inkasoft.soykachimbo.ui.screens.pdf

    import android.content.Context
    import android.graphics.Bitmap
    import android.graphics.pdf.PdfRenderer
    import android.os.ParcelFileDescriptor
    import android.util.DisplayMetrics
    import android.view.WindowManager
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.lazy.rememberLazyListState
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.automirrored.filled.ArrowBack
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.Icon
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.material3.TopAppBar
    import androidx.compose.material3.TopAppBarDefaults
    import androidx.compose.runtime.*
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.asImageBitmap
    import androidx.compose.ui.graphics.ImageBitmap
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavController
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.withContext
    import org.inkasoft.soykachimbo.ui.common.DetailTopBar
    import org.inkasoft.soykachimbo.ui.common.LoadingIndicator
    import org.inkasoft.soykachimbo.ui.screens.Screen
    import org.jetbrains.compose.resources.stringResource
    import org.koin.compose.viewmodel.koinViewModel
    import soykachimbo.composeapp.generated.resources.Res
    import soykachimbo.composeapp.generated.resources.app_name
    import java.io.File
    import java.io.FileOutputStream
    import java.lang.Boolean
    import java.net.URL

    actual class PdfViewer(private val context: Context) {
        actual suspend fun loadPdf(
            url: String
        ): List<ImageBitmap> = withContext(Dispatchers.IO) {
            val tempFile = File.createTempFile("temp_pdf", ".pdf", context.cacheDir)
            URL(url).openStream().use { input -> FileOutputStream(tempFile).use { output -> input.copyTo(output) } }

            val descriptor = ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val renderer = PdfRenderer(descriptor)
            val bitmaps = mutableListOf<ImageBitmap>()

            // Obtener el ancho de la pantalla en pixeles
            val displayMetrics = DisplayMetrics()
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay.getMetrics(displayMetrics)
            val screenWidth = displayMetrics.widthPixels.toFloat()

            for (i in 0 until renderer.pageCount) {

                val page = renderer.openPage(i)

                // Dimensiones originales de la página
                val originalWidth = page.width.toFloat()
                val originalHeight = page.height.toFloat()

                // Escalar el alto proporcionalmente al nuevo ancho
                val scaleFactor = screenWidth / originalWidth
                val newWidth = screenWidth.toInt()
                val newHeight = (originalHeight * scaleFactor).toInt()

                val bitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                bitmaps.add(bitmap.asImageBitmap())
                page.close()
            }
            renderer.close()
            bitmaps
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    actual fun PdfViewerScreen(
        pdfUrl: String,
        modifier: Modifier,
        onBack: () -> Unit
    ) {
        val context = LocalContext.current
        val pdfViewer = remember { PdfViewer(context) }
        val images = remember { mutableStateListOf<ImageBitmap>() }
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        var loading by remember{ mutableStateOf(true) }

        LaunchedEffect(pdfUrl) {
            images.addAll(pdfViewer.loadPdf(pdfUrl))
            loading = false
        }

        val lazyState = rememberLazyListState()

        Screen {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {Text("PDF")},
                        navigationIcon = {
                            IconButton(onClick = onBack){
                                Icon(
                                  imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "Atras"
                                )
                            }
                        }
                    )
                }
            ) { padding ->
                LoadingIndicator(enabled = loading)
                Text("Mostrando el pdf")
                LazyColumn(modifier = modifier.padding(padding), state = lazyState, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(images) { bitmap -> Image(bitmap = bitmap, contentDescription = "PDF Page") }
                }
            }
        }

    }
