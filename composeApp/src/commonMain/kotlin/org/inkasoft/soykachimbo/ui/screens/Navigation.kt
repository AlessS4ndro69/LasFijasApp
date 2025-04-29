package org.inkasoft.soykachimbo.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.inkasoft.soykachimbo.ui.screens.exam.GenerateQuestionsScreen
import org.inkasoft.soykachimbo.ui.screens.home.HomeScreen
import org.inkasoft.soykachimbo.ui.screens.pdf.PdfViewerScreen
import org.inkasoft.soykachimbo.ui.screens.promo.PromoFijas
import org.inkasoft.soykachimbo.ui.screens.question.QuestionScreen


@Composable
fun Navigation(){
    var selectedIndex by remember { mutableStateOf(0) }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedIndex) { index ->
                selectedIndex = index
                when (index) {
                    0 -> navController.navigate("home")
                    1 -> navController.navigate("generateQuestions")
                    2 -> navController.navigate("promo")
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "home",
            //startDestination = "question",
            modifier = Modifier.padding(paddingValues)
        ){
            composable("home"){
                HomeScreen()
            }
            composable("generateQuestions"){
                GenerateQuestionsScreen(
                    navController = navController
                )
            }
            composable("question") {
                QuestionScreen(
                    navigate = { route -> navController.navigate(route)},
                )
            }
            composable("pdfviewer"){
                PdfViewerScreen(
                    pdfUrl="",
                    onBack = {navController.navigateUp()}
                )
            }
            composable("promo"){
                PromoFijas(
                    onBack = {navController.navigateUp()}
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar {
        val items = listOf("Inicio", "Evaluación", "Compartir")
        val icons = listOf(Icons.Default.Home, Icons.Default.Check, Icons.Default.Share)

        items.forEachIndexed { index, title ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = { Icon(imageVector = icons[index], contentDescription = title) },
                label = { Text(title) }
            )
        }
    }
}
