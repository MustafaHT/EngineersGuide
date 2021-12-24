package com.example.engineersguide.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.engineersguide.model.components.ComponentApi

@Database(entities = [ComponentApi::class], version = 2)
abstract class ComponentsDatabase:RoomDatabase() {



    abstract fun componentsDao(): ComponentsDao
}