package org.inkasoft.soykachimbo.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyModalSheet(
    showSheet: Boolean,
    onDismiss: () -> Unit,
    image: String
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true  // Permite la mitad de la pantalla
    )
    var imageHeight by remember { mutableStateOf(0f) }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clipToBounds()
                    .background(color= Color.Cyan)
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = "Imagen",
                    contentScale = ContentScale.FillWidth, // Muestra la parte superior
                    onSuccess = { result -> imageHeight = result.painter.intrinsicSize.height },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                        .graphicsLayer {
                            clip = true
                            translationY = imageHeight * 0.8f
                        },
                )
                // Botón de cerrar en la esquina superior derecha
                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp) // Pequeño margen para evitar tocar el borde
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar")
                }
            }



        }
    }
}
