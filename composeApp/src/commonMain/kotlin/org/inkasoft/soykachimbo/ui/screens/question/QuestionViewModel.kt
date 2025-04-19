package org.inkasoft.soykachimbo.ui.screens.question

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.inkasoft.soykachimbo.data.Question
import org.inkasoft.soykachimbo.data.QuestionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.inkasoft.soykachimbo.ui.common.SharedConfigViewModel


//val QUESTIONSPERCOURSE = 20
//val QUESTIONSALLCOURSES = 80
class QuestionViewModel(
    private val repository: QuestionsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    /// GENERABA CONFLICTOS AL MOMENTO DE ELEGIR ENTRE EXAMEN O PRACTICA POR CURSO

//    init {
//
//        viewModelScope.launch {
//            // Marcar la carga inicial
//            _state.value = _state.value.copy(loading = true)
//        }
//
//        // Cargar preguntas en paralelo
//        viewModelScope.launch {
//            repository.questions.collect { questions ->
//                if (questions.isNotEmpty()) {
//                    _state.value = _state.value.copy(
//                        loading = false,
//                        questions = questions
//                    )
//                }
//            }
//        }
//
//    }


    data class UiState(
        val loading: Boolean = false,
        val questions: List<Question> = emptyList(),
        val error: String? = null,
        val score: Int = 0,
        val course: String = "",
        val dificulty: String = "",
        val showTheory: Boolean = false,
        val showSolution: Boolean = false,
        val showTimer: Boolean = false,
        val showAssist: Boolean = false
    )

    suspend fun getQuestionsByCourse(course: String, nQuestions: Int) {
        _state.update { it.copy(loading = true) }

        repository.fetchQuestionsByCourse(course, nQuestions).collect { questions ->
            _state.update {
                it.copy(
                    loading = false,
                    questions = questions.asSequence().shuffled().take(nQuestions).toList()
                )
            }
        }
    }

    suspend fun getRandomQuestionsAllCourses(nQuestions: Int){
        _state.update { it.copy(loading = true) }

        repository.fetchRandomQuestionsAllCourses(nQuestions).collect { questions ->
            _state.update {
                it.copy(
                    loading = false,
                    questions = questions.asSequence().shuffled().take(nQuestions).toList()
                )
            }
        }
    }

    fun updatePickedAlternative(questionIndex: Int, selection: String) {
        _state.value = _state.value.copy(
            questions = _state.value.questions.mapIndexed { index, question ->
                if (index == questionIndex) question.copy(pickedAlternative = selection) else question
            }
        )
    }

    fun incrementScore() {
        _state.value = _state.value.copy(score = _state.value.score + 1)
    }
}
