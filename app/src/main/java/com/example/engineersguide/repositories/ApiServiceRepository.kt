package com.example.engineersguide.repositories

import android.content.Context
import com.example.engineersguide.api.ComponentsAPI
import com.example.engineersguide.model.components.ComponentApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
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

    suspend fun addComponent(
        component: ComponentApi
    ) =
        retrofitApi.addComponents(component)

    suspend fun deleteComponent(ComponentsId: Int) =
        retrofitApi.deleteComponents(ComponentsId)

    suspend fun updataComponent(componentId: Int,component: ComponentApi) =
        retrofitApi.updateComponents(componentId,component)

//    suspend fun uploadUserImage(file: File) : Response<ComponentApi> {
//
//        // Create RequestBody instance from file with content type: Image/* or you can change it with any type you want from this link below
//        // https://www.lifewire.com/file-extensions-and-mime-types-3469109
//        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//
//        // MultipartBody.Part is used to send also the actual file name
//        val body = MultipartBody.Part.createFormData("imageFile",file.name,requestFile)
//
//        // finally, execute the request
//        return retrofitApi.addComponents()
//    }


    companion object {
        private var instance: ApiServiceRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = ApiServiceRepository(context)
        }

        fun get(): ApiServiceRepository {
            return instance ?: throw Exception("ApiServiceRepository must be initialized ")
        }
    }


}