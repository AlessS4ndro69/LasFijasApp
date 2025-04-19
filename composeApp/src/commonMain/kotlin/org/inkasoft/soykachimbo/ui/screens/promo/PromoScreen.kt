package org.inkasoft.soykachimbo.ui.screens.promo

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import soykachimbo.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import soykachimbo.composeapp.generated.resources.allDrawableResources
import soykachimbo.composeapp.generated.resources.fijas_promo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PromoFijas(
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo
        Image(
            painter = painterResource(Res.drawable.fijas_promo),
            contentDescription = "Promo Las Fijas",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        BoxWithConstraints {
            val screenHeight = maxHeight
            val screenWidth = maxWidth

            // 🔄 Detecta interacciones (como presionar)
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val animatedElevation by animateDpAsState(
                targetValue = if (isPressed) 2.dp else 8.dp,
                label = "buttonElevation"
            )

            // 🧨 Botón animado
            OutlinedButton(
                onClick = { /* Acción de compartir */ },
                modifier = Modifier
                    .offset(
                        x = screenWidth * 0.25f,
                        y = screenHeight * 0.62f
                    )
                    .width(screenWidth * 0.5f)
                    .height(50.dp)
                    .shadow(
                        elevation = animatedElevation,
                        shape = RoundedCornerShape(30.dp)
                    ),
                interactionSource = interactionSource,
                border = BorderStroke(2.dp, Color.White),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {

            }
        }
    }
}

