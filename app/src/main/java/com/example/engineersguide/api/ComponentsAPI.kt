package com.example.engineersguide.api

import com.example.engineersguide.model.components.ComponentApi
import retrofit2.Response
import retrofit2.http.*

interface ComponentsAPI {

    @GET("/api/v1/component")
    suspend fun getComponents(
//        @Header("Authorization") token: String
    ): Response<List<ComponentApi>>


    @POST("/api/v1/component")
    suspend fun addComponents(
        @Body component: ComponentApi
    ): Response<ComponentApi>

    @DELETE("/api/v1/component/1")
    suspend fun deleteComponents(
        @Query("componentsId") componentsId: Int
    ): Response<ComponentApi>

//    suspend fun addComponents(): Call<Components>

}