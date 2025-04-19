package org.inkasoft.soykachimbo.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.inkasoft.soykachimbo.data.local.Course
import org.inkasoft.soykachimbo.ui.screens.exam.GenerateQuestionViewModel.UiState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import soykachimbo.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
class SharedConfigViewModel() : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch{
            _state.value = _state.value.copy(loading = true)
            val bytes = Res.readBytes("files/catalog.json")
            val jsonString = bytes.decodeToString()

            _state.value = _state.value.copy(
                loading = false,
                listCourses = Json.decodeFromString<List<Course>>(jsonString)
            )
        }
    }

    data class UiState(
        val loading: Boolean = true,
        val currentCourse: String = "algebra",
        val displayCurrentCourse: String = "Algebra",
        val listCourses: List<Course> = emptyList(),
        val difficulty: String = "preuniversitario",
        val showTheory: Boolean = false,
        val showSolution: Boolean = false,
        val showTimer: Boolean = false,
        val showAssist: Boolean = false,
        val questionsType: String = "Examen",
        val numberQuestions: Int = 10
    )

    fun updateConfig(
        displayCurrentCourse: String,
        difficulty: String,
        showTheory: Boolean,
        showSolution: Boolean,
        showTimer: Boolean,
        showAssist: Boolean,
        questionsType: String,
        numberQuestions: Int
    ) {
        _state.value = _state.value.copy(
            displayCurrentCourse = displayCurrentCourse,
            difficulty = difficulty,
            showTheory = showTheory,
            showSolution = showSolution,
            showTimer = showTimer,
            showAssist = showAssist,
            questionsType = questionsType,
            numberQuestions = numberQuestions
        )
        println("Updateconfig -> course: ${_state.value.currentCourse}, difficulty: ${_state.value.difficulty}, showTimer: ${_state.value.showTimer}")

    }

    fun updateNumberQuestions(n: String){
        _state.update { it.copy(numberQuestions = n.toInt())}
    }

    fun updateQuestionType(type: String){
        _state.update { it.copy(questionsType = type) }
    }

    fun updateDifficulty(difficulty: String) {
        _state.value = _state.value.copy(difficulty = difficulty)
    }

    fun updateCourse(displayCourse: String){
        val nameCourse = _state.value.listCourses.find { it.display == displayCourse }?.name
        println("nameCourse find: $nameCourse")
        _state.update { it.copy(
            currentCourse = nameCourse?:"",
            displayCurrentCourse = displayCourse
            )
        }
    }

    fun onChangeShowTimer(){
        _state.update {it.copy(showTimer = !it.showTimer)}
    }

    fun onChangeShowSolution(){
        _state.update {it.copy(showSolution = !it.showSolution)}
    }

    fun onChangeShowAssist(){
        _state.update {it.copy(showAssist = !it.showAssist)}
    }

    fun onChangeShowTheory(){
        _state.update {it.copy(showTheory = !it.showTheory)}
    }


}
