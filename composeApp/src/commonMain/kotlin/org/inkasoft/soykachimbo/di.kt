package org.inkasoft.soykachimbo

import androidx.room.RoomDatabase
import io.inkasoft.soykachimbo.BuildConfig

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.inkasoft.soykachimbo.data.QuestionsRepository
import org.inkasoft.soykachimbo.data.database.QuestionsDao
import org.inkasoft.soykachimbo.data.database.QuestionsDatabase
import org.inkasoft.soykachimbo.data.remote.QuestionsService
import org.inkasoft.soykachimbo.ui.common.SharedConfigViewModel
import org.inkasoft.soykachimbo.ui.screens.exam.GenerateQuestionViewModel
import org.inkasoft.soykachimbo.ui.screens.question.QuestionViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

val appModule = module {
    single(named("apiKey")){ BuildConfig.API_KEY}
    // registra una instancia unica de MoviesDao en toda la aplicación
    single<QuestionsDao>{
        val dbBuilder = get<RoomDatabase.Builder<QuestionsDatabase>>()
        dbBuilder.build().questionsDao()
    }
}

val dataModule = module{
    factoryOf(::QuestionsRepository)
    factoryOf(::QuestionsService)
    single {
        HttpClient {
            install(ContentNegotiation){
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest){
                url{
                    protocol = URLProtocol.HTTPS
                    host = "4z8a17l4fb.execute-api.us-east-1.amazonaws.com"
                }
            }
        }

    }
}

val viewModelsModule = module {
    viewModelOf(::GenerateQuestionViewModel)
    single { SharedConfigViewModel() }
    viewModelOf(::QuestionViewModel)
}

expect val nativeModule: Module

fun initKoin(config: KoinAppDeclaration? = null){
    startKoin{
        config?.invoke(this)
        modules(appModule, dataModule, viewModelsModule, nativeModule)
    }
}