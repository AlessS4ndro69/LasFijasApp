package org.inkasoft.soykachimbo.ui.screens.exam

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.inkasoft.soykachimbo.data.Question
import org.inkasoft.soykachimbo.data.local.Course

import org.inkasoft.soykachimbo.ui.common.DetailTopBar
import org.inkasoft.soykachimbo.ui.common.DropdownButton
import org.inkasoft.soykachimbo.ui.common.SharedConfigViewModel
import org.inkasoft.soykachimbo.ui.screens.Screen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import soykachimbo.composeapp.generated.resources.Res
import soykachimbo.composeapp.generated.resources.app_name



const val appVersion = "1.0"
//val listCourses = listOf("Algebra", "Biologia", "Raz. Matematica", "Fisica", "Quimica")

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun GenerateQuestionsScreen(
    vm: SharedConfigViewModel = koinViewModel(),
    navController: NavController
) {

    //var bytes by remember { mutableStateOf(ByteArray(0)) }
    //var listCourses by remember { mutableStateOf<List<Course>>(emptyList()) }

//    LaunchedEffect(Unit) {
//        bytes = Res.readBytes("files/catalog.json")
//        val jsonString = bytes.decodeToString()
//        listCourses = Json.decodeFromString<List<Course>>(jsonString)
//    }


    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val state by vm.state.collectAsState()
    val currentCourse = state.currentCourse
    val currentDifficulty = state.difficulty
    val listCourses = state.listCourses
    val numberQuestions = state.numberQuestions


    Screen {
        Scaffold(
            topBar = { DetailTopBar(title = stringResource(Res.string.app_name), scrollBehavior = scrollBehavior) }
        ) { padding ->
            //DetailBody(modifier = Modifier.padding(padding), navController = navController, updateConfig = { vm.updateConfig()})

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                DropDownRow("Actividad", listOf("Examen", "Practica"), state.questionsType) { vm.updateQuestionType(it)}


                if (state.questionsType == "Practica" && listCourses.isNotEmpty()) {
                    DropDownRow("Curso", listCourses.map { it -> it.display }, state.displayCurrentCourse) {vm.updateCourse(it)}
                }

                DropDownRow("Dificultad", listOf("Básico", "Colegio", "Preuniversitario"), state.difficulty) {vm.updateDifficulty(it) }
                state.questionsType.takeIf { it == "Practica" }?.let {
                    DropDownRow("Cantidad de preguntas", listOf("10","20"), state.numberQuestions.toString()) {vm.updateNumberQuestions(it.toString())}
                }
                state.questionsType.takeIf { it == "Examen" }?.let {
                    DropDownRow("Cantidad de preguntas", listOf("40","80"), state.numberQuestions.toString()) {vm.updateNumberQuestions(it.toString())}
                }

                CheckboxRow("Visualizar teoría", state.showTheory) {vm.onChangeShowTheory()}
                CheckboxRow("Mostrar solucionario al finalizar", state.showSolution) {vm.onChangeShowSolution()}
                CheckboxRow("Mostrar temporizador", state.showTimer) { vm.onChangeShowTimer()}
                CheckboxRow("Mostrar ayuda", state.showAssist) { vm.onChangeShowAssist()}

                OutlinedButton(
                    onClick = {
                        vm.updateConfig(
                            displayCurrentCourse = state.displayCurrentCourse,
                            difficulty = state.difficulty,
                            showTheory = state.showTheory,
                            showSolution = state.showSolution,
                            showTimer = state.showTimer,
                            showAssist = state.showAssist,
                            questionsType = state.questionsType,
                            numberQuestions = state.numberQuestions
                        )
                            navController.navigate("question")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Iniciar")
                }

//                Text("Dificultad: $currentDifficulty")
//                Text("Visualizar teoría: $state.showTeory")
//                Text("Mostrar solución: $state.showSolution")
//                Text("Mostrar temporizador: $state.showTimer")
//                Text("Mostrar ayuda: $state.showAssist")
            }
        }
    }
}


@Composable
private fun DropDownRow(
    label: String,
    options: List<String>,
    selectedValue: String,
    onValueChange: (String) -> Unit,
) {
    RowBody(text = label) {
        DropdownButton(options = options, selectedValue = selectedValue, onValueChange = onValueChange)
    }
}

@Composable
private fun CheckboxRow(
    label: String,
    state: Boolean,
    onStateChange: (Boolean) -> Unit
) {
    RowBody(text = label) {
        Checkbox(checked = state, onCheckedChange = onStateChange)
    }
}

@Composable
private fun RowBody(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, modifier = Modifier.weight(1f))
        content()
    }
}








