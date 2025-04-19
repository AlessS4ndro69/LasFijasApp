package org.inkasoft.soykachimbo.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import soykachimbo.composeapp.generated.resources.Res
import soykachimbo.composeapp.generated.resources.allDrawableResources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ImageSlider(
    images: List<String>,
    pagerState: PagerState // para tener la paginacion en el padre y controlar los datos de cada question
) {
    var loading by remember { mutableStateOf(true) }
    val errorImageRes = Res.allDrawableResources["errorimage"]

    HorizontalPager(state = pagerState) { page ->

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp,
                focusedElevation = 6.dp,
                hoveredElevation = 6.dp,
                disabledElevation = 0.dp
            )
        ) {

            LoadingIndicator(enabled = loading)

            if (errorImageRes != null){
                AsyncImage(
                    model = images[page],
                    contentDescription = "Imagen $page",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize(),
                    onError = { println("error al cargar la imagen con asyncimage") },
                    error = painterResource(errorImageRes),
                    onSuccess = {loading = false}
                )
            }

            //Text("page is: $page")
        }
    }
}