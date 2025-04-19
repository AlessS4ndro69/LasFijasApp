package org.inkasoft.soykachimbo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.inkasoft.soykachimbo.data.Question

@Dao
interface QuestionsDao {

    @Query("SELECT * FROM Question")
    fun fetchQuestions(): Flow<List<Question>>

    @Query("SELECT * FROM Question WHERE course = :course")
    fun fetchQuestionByCourse(course: String): Flow<List<Question>>

    @Query("SELECT * FROM Question WHERE id = :id")
    fun fetchQuestionById(id: Int): Flow<Question?>

    @Query("SELECT * FROM Question")
    fun fetchRandomQuestionsAllCourses(): Flow<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(questions: List<Question>)

    @Query("DELETE FROM Question WHERE id = :id")
    suspend fun deleteQuestionById(id: Int)




}