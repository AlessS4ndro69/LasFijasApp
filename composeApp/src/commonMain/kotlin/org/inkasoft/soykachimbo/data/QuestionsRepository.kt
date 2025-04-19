package org.inkasoft.soykachimbo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import org.inkasoft.soykachimbo.data.database.QuestionsDao
import org.inkasoft.soykachimbo.data.remote.QuestionsService
import org.inkasoft.soykachimbo.data.remote.RemoteQuestion

class QuestionsRepository(
    private val questionsService: QuestionsService,
    private val questionsDao: QuestionsDao
) {
    // se ejecuta en la instancia del objeto, no espera un collect
    val questions: Flow<List<Question>> = questionsDao.fetchRandomQuestionsAllCourses().onEach { questions ->
        if (questions.isEmpty()){
            val questionsResponse = questionsService.fetchRandomQuestionsAllCourses().results.map { it.toDomainQuestion() }
            questionsDao.save(questionsResponse)
        }
    }


    suspend fun fetchRandomQuestionsAllCourses(nQuestions: Int): Flow<List<Question>> = questionsDao.fetchRandomQuestionsAllCourses().onEach { questions ->
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val questionsValidUrlSigned = questions.filter { it.expirationTime > currentTime }
        val expiredQuestions = questions.filter { it.expirationTime <= currentTime }
        expiredQuestions.map { it -> questionsDao.deleteQuestionById(it.id) }

        if(questionsValidUrlSigned.size <= nQuestions){
            val questionsResponse = questionsService.fetchRandomQuestionsAllCourses().results.map { it.toDomainQuestion() }
            questionsDao.save(questionsResponse + questionsValidUrlSigned)
        }
    }

    // evaluar cuando no existen datos en el Dao, puesto que no se emite nada //
    suspend fun fetchQuestionsByCourse(course: String, nQuestions: Int): Flow<List<Question>> = questionsDao.fetchQuestionByCourse(course).onEach { questions ->
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val questionsValidUrlSigned = questions.filter { it.expirationTime > currentTime }
        val expiredQuestions = questions.filter { it.expirationTime <= currentTime }
        expiredQuestions.map { it -> questionsDao.deleteQuestionById(it.id) }

        if(questionsValidUrlSigned.size <= nQuestions){
            val questionsResponse = questionsService.fetchQuestionsByCourse(course).results.map { it.toDomainQuestion() }
            questionsDao.save(questionsResponse + questionsValidUrlSigned)
        }
    }

    suspend fun deleteQuestionById(id: Int): Unit {
        questionsDao.deleteQuestionById(id)
    }


    suspend fun updatePickedAlternative(question: Question, pickedAlternative: String){
        questionsDao.save(listOf(question.copy(pickedAlternative = pickedAlternative)))
    }






}

private fun RemoteQuestion.toDomainQuestion() = Question(
    url = url,
    answer = metadata.answer,
    topic = metadata.topic,
    topicCode = metadata.topicCode,
    course = metadata.course,
    correctAlternative = metadata.correctAlternative,
    courseCode = metadata.courseCode,
    solutionUrl = metadata.solutionUrl,
    expirationTime = metadata.expirationTime * 1000
)