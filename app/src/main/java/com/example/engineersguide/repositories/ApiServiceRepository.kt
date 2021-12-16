package com.example.engineersguide.repositories

import android.content.Context
import com.example.engineersguide.api.ComponentsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val SHARED_PREF_FILE = "Auth"
private const val BASE_URL = "https://61ad8ee6d228a9001703ae27.mockapi.io"
class ApiServiceRepository(context: Context) {

    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val sharedPref = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)


    val retrofitApi = retrofitService.create(ComponentsAPI::class.java)



    suspend fun getComponents() =
        retrofitApi.getComponents()

    suspend fun addComponent(title:String,dec:String, functionality:String, equ:String, rec1:String,rec2:String,rec3:String) =
        retrofitApi.addComponents(title,dec,functionality,equ,rec1,rec2,rec3)

    suspend fun deleteComponent(ComponentsId:Int) =
        retrofitApi.deleteComponents(ComponentsId)


    companion object {
        private var instance: ApiServiceRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = ApiServiceRepository(context)
        }

        fun get() : ApiServiceRepository {
            return instance ?: throw Exception("ApiServiceRepository must be initialized ")
        }
    }

}