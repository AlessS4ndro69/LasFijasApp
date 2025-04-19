package org.inkasoft.soykachimbo.ui.screens.exam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.inkasoft.soykachimbo.data.Question
import org.inkasoft.soykachimbo.data.local.Course
import org.inkasoft.soykachimbo.data.remote.QuestionsService
import org.jetbrains.compose.resources.ExperimentalResourceApi
import soykachimbo.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
class GenerateQuestionViewModel(
    private val questionsService: QuestionsService
):ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)

            val bytes = Res.readBytes("files/catalog.json")
            val jsonString = bytes.decodeToString()


            state = UiState(
                loading = false,
                courses = Json.decodeFromString<List<Course>>(jsonString)
            )
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val courses: List<Course> = emptyList()

    )
}
