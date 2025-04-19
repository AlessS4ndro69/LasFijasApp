package org.inkasoft.soykachimbo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConfigApp (
    val sizeQuestions: Int = 0
){
    companion object{
        fun default() = ConfigApp()
    }
}
