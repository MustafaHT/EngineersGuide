package com.example.engineersguide.api

import androidx.room.Update
import com.example.engineersguide.model.components.ComponentApi
import retrofit2.Response
import retrofit2.http.*

interface ComponentsAPI {

    @GET("/api/v1/component")
    suspend fun getComponents(

    ): Response<List<ComponentApi>>

    @POST("/api/v1/component")
    suspend fun addComponents(
        @Body component: ComponentApi
    ): Response<ComponentApi>

    @DELETE("/api/v1/component/{id}")
    suspend fun deleteComponents(
        @Path("id") componentsId: Int
    ): Response<ComponentApi>

    @PUT("/api/v1/component/{id}")
    suspend fun updateComponents(
       @Path("id") componentId: Int,
       @Body component:ComponentApi
    ): Response<ComponentApi>

}