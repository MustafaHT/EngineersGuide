package com.example.engineersguide.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.engineersguide.model.components.ComponentModel

@Dao
interface ComponentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponents(ComponentApi: List<ComponentModel>)


    @Query("SELECT * FROM componentmodel")
    suspend fun getComponents(): List<ComponentModel>

}