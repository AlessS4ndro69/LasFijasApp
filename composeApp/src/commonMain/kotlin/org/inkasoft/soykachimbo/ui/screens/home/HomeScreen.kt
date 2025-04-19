package org.inkasoft.soykachimbo.ui.screens.home


import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults

import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.inkasoft.soykachimbo.ui.screens.Screen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import soykachimbo.composeapp.generated.resources.Res
import soykachimbo.composeapp.generated.resources.allDrawableResources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Screen {
        Scaffold(
        ) {
            Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
                BannerSection()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Novedades", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 16.dp))
                NewsSection(modifier = Modifier)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Categorías", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 16.dp))
                CategoriesSection(modifier = Modifier)
            }
        }
    }


}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BannerSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),

        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Res.allDrawableResources["soykachimbobanner"]?.let {
                Image(painterResource(it), contentDescription = "Estudiante", modifier = Modifier.fillMaxWidth())
            }

        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NewsSection(
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Res.allDrawableResources["news2"]?.let {
            MyCard(modifier = Modifier.fillMaxWidth(0.50f).padding(horizontal =  3.dp),"Retroalimentación", "Estudia con preguntas y resolución",it)
        }
        Res.allDrawableResources["news1"]?.let {
            MyCard(modifier = Modifier.padding( horizontal =  3.dp),"Teoría", "Cada pregunta tiene su respectiva teoría",it)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CategoriesSection(
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Res.allDrawableResources["news1"]?.let {
            MyCard(
                modifier = modifier
                    .fillMaxWidth(0.5f)
                    .padding(horizontal = 3.dp),
                title = "Examen simulacro",
                subtitle = "Elige la cantidad de preguntas que desees",
                imageRes = it
            )
        }
        Res.allDrawableResources["news2"]?.let {
            MyCard(
                modifier = modifier.padding(horizontal = 3.dp),
                title = "Práctica",
                subtitle = "Elige el curso que desees reforzar",
                imageRes = it
            )
        }


    }
}

@Composable
fun MyCard(
    modifier: Modifier,
    title: String,
    subtitle: String,
    imageRes: DrawableResource
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            focusedElevation = 6.dp,
            hoveredElevation = 6.dp,
            disabledElevation = 0.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.padding(6.dp)
        ) {
            Text(title, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }


    }
}