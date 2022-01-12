package com.example.engineersguide.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.engineersguide.model.components.ComponentModel

@Database(entities = [ComponentModel::class], version = 1)
abstract class ComponentsDatabase : RoomDatabase() {


    abstract fun componentsDao(): ComponentsDao
}