package com.example.engineersguide.repositories

import android.content.Context
import androidx.room.Room
import com.example.engineersguide.database.ComponentsDatabase
import com.example.engineersguide.model.components.ComponentApi


private const val DATABASE_NAME = "components-database"
class RoomServiceRepository(context:Context) {

    private val database = Room.databaseBuilder(
        context,
        ComponentsDatabase::class.java,
        DATABASE_NAME,
    ).fallbackToDestructiveMigration().build()


    private val componentsDao = database.componentsDao()

    suspend fun insertComponents(components: List<ComponentApi>) =
        componentsDao.insertComponents(components)

    suspend fun getComponents() = componentsDao.getComponents()


    companion object {
        private var instance: RoomServiceRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = RoomServiceRepository(context)
        }

        fun get(): RoomServiceRepository {
            return instance ?: throw Exception("Room Service Repository must be initialized")
        }
    }

}