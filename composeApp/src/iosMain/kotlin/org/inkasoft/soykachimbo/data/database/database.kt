package org.inkasoft.soykachimbo.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<QuestionsDatabase>{
    val dbFilePath = NSHomeDirectory() + "/$DATABASE_NAME"
    return Room.databaseBuilder<QuestionsDatabase>(
        name = dbFilePath,
        factory = {QuestionsDatabase::class.instantiateImpl()}
    ).setDriver(BundledSQLiteDriver())
}