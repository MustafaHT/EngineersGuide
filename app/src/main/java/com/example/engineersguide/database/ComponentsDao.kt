package com.example.engineersguide.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.engineersguide.model.components.ComponentApi

@Dao
interface ComponentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponents(ComponentApi: List<ComponentApi>)


    @Query("SELECT * FROM componentapi")
    suspend fun getComponents():List<ComponentApi>

}