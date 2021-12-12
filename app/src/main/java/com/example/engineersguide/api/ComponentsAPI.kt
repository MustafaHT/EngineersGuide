package com.example.engineersguide.api

import com.example.engineersguide.model.components.Components
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ComponentsAPI {

    @GET("")
    suspend fun getComponents(
//        @Header("Authorization") token: String
    ) : Response<Components>

    @POST("")
    suspend fun addComponents(
        @Header("Authorization") userId:String
    ) : Response<Components>

    @DELETE("")
    suspend fun deleteComponents(
        @Header("Authorization") userId: String
    ) : Response<Components>

}