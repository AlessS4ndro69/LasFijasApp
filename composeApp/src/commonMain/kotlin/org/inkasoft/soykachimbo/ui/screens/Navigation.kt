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
                    pdfUrl="https://questions-soykachimbo.s3.amazonaws.com/Algebra/PDF/PRACTICA%2003.pdf?AWSAccessKeyId=ASIA6GNMNEGJ5TWWZFKP&Signature=T0vgPLb7WiXYXYhcgw6uhnBtHsI%3D&x-amz-security-token=IQoJb3JpZ2luX2VjENP%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJIMEYCIQCDhDSTdUjr9OH7%2BxuMAXmQwH44Kaq%2FqK4ShAni832zIQIhAJ%2FIAoK2ScGhqf%2FN2aK%2B4Oebu5R1FQHTVLzQeLXVLJJdKpcDCDwQABoMOTc1ODU2MDE3ODExIgwRylIe%2BO0A4aq%2BPS8q9AKV%2BmgwUkGf5Ku6MhH2jPPdLig%2BPZ%2FUeIS4bDNXHO%2BIOil%2BhJiJubT6UYECfzft62%2FKGxeax8AxcK2OgkiWeQPjQEL%2BaWV2bNL3dWWuxsClVRER%2BLmBTthgNL1BpQrNuUgFI7SpFWNwYPx4JqX%2Fq6Z0VALEApLaj%2B2fL0WKVdaX77AEyAe62vKehGEhDrqDAnTIDfVrwkUI6quyr0ivBUWnrfSKFh990TsivsGnEiMbg6lKojFOE3%2BLKGAi0Qoug05unG1InP5Rgnre1%2BYIAwcvs84gbi6PV5M1IrBuqM7C4zB5N3dDmO%2BPZ59ts6ZLgX2WyTJORIYEYJXbd489J97KAQJmGVvVM%2B7SKVgH3ah0Y4nw1AQXCjUFkzjESziduSZw46OLb%2Bhcy1RQwtFVRdKzLVYSX8%2FuADkYkZ6SyuiJ%2FyOH%2BP7gnREBdOdE%2BAzzsZ9nWDOxnGkfgrbxsD0mDEhwcCjNw58ugQ8D6q1ehjI7TIrQPVgwh%2FuSvwY6nAG52c8MD5ehIZm9rVyWcuoRSH36B5WVORPVMNTPmQMXa%2F%2BxIG3lEzWPlZpvC%2FeYxUsIQ%2FPKjy7gM9%2FDA5HBxCPyYqqXp87aoHMmTSkJqNg91FQ17TIwd9aeMYX03xOHBeBYNxFnhKmP3KZAtAKBcZT0nhG9pTcLLdhykjzcp6RE3N0zIA8DWI9TpghUwHzFrT7%2BkE8J53acWc1qTPM%3D&Expires=1743047576",
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