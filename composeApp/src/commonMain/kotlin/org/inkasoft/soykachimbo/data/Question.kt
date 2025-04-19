package org.inkasoft.soykachimbo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val url: String = "",
    val answer: String = "No hay preguntas disponibles",
    val topic: String = "",
    val topicCode: Int = 0,
    val course: String = "",
    val correctAlternative: String = "No hay alternativa disponible",
    val courseCode: Int = 0,
    val pickedAlternative: String = "",
    val dificulty: String = "",
    val solutionUrl: String = "",
    val expirationTime: Long = 0
){
    companion object{
        fun default() = Question()
    }
}