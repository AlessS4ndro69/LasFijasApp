package org.inkasoft.soykachimbo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.inkasoft.soykachimbo.data.Question

const val DATABASE_NAME = "questions.db"

// Una interfaz solo define contratos de comportamiento. No almacena estado ni tiene constructores.
// Solo tiene métodos sin implementación, pero puede tener métodos con default en Kotlin.
interface DB {
    fun clearAllTables()
}

@Database(entities = [Question::class], version = 1)
abstract class QuestionsDatabase: RoomDatabase(), DB {
    abstract fun questionsDao(): QuestionsDao  // Expone el DAO, Room genera automaticamente la implementacion de este metodo cuando se compila la app

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }
}