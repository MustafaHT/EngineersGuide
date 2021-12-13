package com.example.engineersguide.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.engineersguide.model.components.Components

@Dao
interface ComponentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponents(Components: List<Components>)


    @Query("SELECT * FROM components")
suspend fun getComponents():List<Components>

}