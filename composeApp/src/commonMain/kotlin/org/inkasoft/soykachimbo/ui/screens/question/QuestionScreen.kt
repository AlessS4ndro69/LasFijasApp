package org.inkasoft.soykachimbo.ui.screens.question

import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.http.HttpHeaders.Date

import kotlinx.coroutines.delay
import org.inkasoft.soykachimbo.ui.common.DetailTopBar
import org.inkasoft.soykachimbo.ui.common.ImageSlider
import org.inkasoft.soykachimbo.ui.common.LoadingIndicator
import org.inkasoft.soykachimbo.ui.common.MyModalSheet
import org.inkasoft.soykachimbo.ui.common.SharedConfigViewModel
import org.inkasoft.soykachimbo.ui.screens.Screen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import soykachimbo.composeapp.generated.resources.Res
import soykachimbo.composeapp.generated.resources.app_name
import kotlinx.datetime.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    vm: QuestionViewModel = koinViewModel(),
    navigate: (String) -> Unit,
    vmConfig: SharedConfigViewModel = koinViewModel(),
    //course: String
){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val state by vm.state.collectAsState() // usando flowState
    val config by vmConfig.state.collectAsState()

    val numberQuestions =  state.questions.size
    val pagerState = rememberPagerState( pageCount = { numberQuestions.takeIf { it > 0 } ?: 1 })
    var currentPage by remember { mutableStateOf(pagerState.currentPage) }
    val currentQuestion = state.questions.getOrNull(currentPage)
    var showSheet by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }

    LaunchedEffect(config.questionsType, config.displayCurrentCourse) {
        config.questionsType.takeIf { it == "Practica" }?.let {
            println("Se deetecto un cambio en questionType: ${config.questionsType}")
            println("Se detecto un cambio en course: ${config.currentCourse}")
            vm.getQuestionsByCourse(config.currentCourse, config.numberQuestions)
        }

        config.questionsType.takeIf { it == "Examen" }?.let {
            vm.getRandomQuestionsAllCourses(config.numberQuestions)
        }
    }


    Screen {
        Scaffold(
            topBar = {
                DetailTopBar(
                    title = stringResource(Res.string.app_name),
                    scrollBehavior = scrollBehavior
                )
            }
        ) { padding->
            LoadingIndicator(enabled = state.loading)

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            currentQuestion?.let {
                                Text(text = "Curso: ${it.course}")
                                Text(text = "Tema: ${it.topic}")
                            }
                        }
                        ScoreCard(state.score, numberQuestions)
                    }
                }

                item {
                    currentQuestion?.let {
                        DetailQuestion(
                            currentPage = currentPage,
                            pagerState = pagerState,
                            numberQuestions = numberQuestions,
                            images = state.questions.map { it.url },
                            selectedAnswer = it.pickedAlternative,
                            onPickedAlternative = { selection ->
                                vm.updatePickedAlternative(currentPage, selection)

                                val updatedQuestion = vm.state.value.questions[currentPage]
                                if (updatedQuestion.pickedAlternative == updatedQuestion.correctAlternative) {
                                    vm.incrementScore()
                                }
                            }
                        )
                    }
                }
//                item {
//                    Column(
//                        modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 30.dp),
//                        horizontalAlignment = Alignment.Start
//                    ) {
//                        currentQuestion?.let{
//                            Text("Clave: ${it.correctAlternative}")
//                            Text("Clave seleccionada: ${it.pickedAlternative}")
//                            Text("course desde generatescreen: ${config.displayCurrentCourse}")
//                            Text("showTimer: ${config.showTimer}")
//                            Text("Id: ${it.id}")
//                            Text("dificulty: ${config.difficulty}")
//                            Text("expiration_time: ${it.expirationTime}")
//
//                        }
//                    }
//                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (config.showTimer and !config.loading){
                            TimerScreen()
                        }
                        if(config.showAssist){
                            TextButton(onClick = { showSheet = true }) {
                                Text("Necesito ayuda")
                            }
                        }

                        currentQuestion?.let{
                            MyModalSheet(
                                showSheet = showSheet,
                                onDismiss = { showSheet = false },
                                image = it.solutionUrl
                            )
                        }

                    }
                    if (config.showTheory) {
                        TextButton(onClick = { navigate("pdfviewer") }) {
                            Text("Recordar teoría")
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun DetailQuestion(
    modifier: Modifier = Modifier,
    currentPage: Int,
    pagerState: PagerState,
    numberQuestions: Int,
    images: List<String>,
    onPickedAlternative: (String) -> Unit,
    selectedAnswer: String
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Problema ${currentPage + 1} de ${numberQuestions}"
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            ImageSlider(
                images = images,
                pagerState = pagerState
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "Marca la alternativa correcta"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            AnswerOptions(
                options = listOf("A","B","C","D","E"),
                pickedAlternative = selectedAnswer,
                    onPickedAlternative = onPickedAlternative
            )
        }

    }
}

@Composable
fun ScoreCard(
    correct: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(70.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "$correct", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$total", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}



@Composable
fun TimerScreen() {
    var timeLeft by remember { mutableStateOf(60) }

    LaunchedEffect(timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
    }

    Row {
        Icon(
            Icons.Rounded.Check,
            contentDescription = "Cronómetro",
            tint = MaterialTheme.colorScheme.primary
        )
        Text(text = "00:${timeLeft}s")
    }
}

@Composable
fun AnswerOptions(
    options: List<String>,
    pickedAlternative: String,
    onPickedAlternative: (String) -> Unit
) {
    MultiChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEach { option ->
            SegmentedButton(
                enabled = pickedAlternative.isEmpty(),
                checked = option == pickedAlternative,
                onCheckedChange = { onPickedAlternative(option) },
                shape = SegmentedButtonDefaults.baseShape,
                label = { Text(option) }
            )
        }
    }
}

fun isUrlExpired(expirationTime: Long): Boolean {
    val currentTime = Clock.System.now().toEpochMilliseconds()
    println("currentTime: $currentTime")
    println("expirationTime: $expirationTime")

    val diffMillis = expirationTime - currentTime
    val diffSeconds = diffMillis / 1000

    if (currentTime > expirationTime) {
        println("⚠️ Se expiró el enlace hace ${-diffSeconds} segundos")
        return true
    } else {
        println("⏳ El enlace expira en $diffSeconds segundos")
        return false
    }
}



