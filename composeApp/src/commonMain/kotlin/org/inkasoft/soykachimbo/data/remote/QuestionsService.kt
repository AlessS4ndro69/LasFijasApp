package org.inkasoft.soykachimbo.data.remote

import io.inkasoft.soykachimbo.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class QuestionsService(
    private val client: HttpClient,
) {
    private val limitQuestions = BuildConfig.LIMIT_QUESTIONS

    suspend fun fetchQuestionsByCourse(course: String): RemoteResult{
        return client
            .get("/prod/get-question"){
                parameter("folder_path",course)
            }
            .body<RemoteResult>()
            //.get("https://4z8a17l4fb.execute-api.us-east-1.amazonaws.com/prod/get-question?folder_path=Algebra")
            //.body<RemoteResult>()
    }

    suspend fun fetchRandomQuestionsAllCourses(): RemoteResult{
        return client
            .get("/prod/getRandomQuestionsAllCourses"){
                parameter("limit", limitQuestions) //limitQuestions esta en di con buildconfig
            }
            .body<RemoteResult>()
    }
}