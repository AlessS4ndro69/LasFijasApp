package org.inkasoft.soykachimbo.data.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.inkasoft.soykachimbo.data.ConfigApp

@Dao
interface ConfigAppDao {
    @Query("SELECT * FROM ConfigApp")
    fun fetchConfigApp(): Flow<ConfigApp>
}