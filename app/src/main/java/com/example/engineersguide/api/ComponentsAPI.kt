package com.example.engineersguide.api

import com.example.engineersguide.model.components.ComponentModel
import retrofit2.Response
import retrofit2.http.*

interface ComponentsAPI {

    @GET("/api/v1/components")
    suspend fun getComponents(

    ): Response<List<ComponentModel>>

    @POST("/api/v1/components")
    suspend fun addComponents(
        @Body component: ComponentModel
    ): Response<ComponentModel>

    @DELETE("/api/v1/components/{id}")
    suspend fun deleteComponents(
        @Path("id") componentsId: Int
    ): Response<ComponentModel>

    @PUT("/api/v1/components/{id}")
    suspend fun updateComponents(
       @Path("id") componentId: Int,
       @Body component:ComponentModel
    ): Response<ComponentModel>

//    suspend fun uploadImage(
//        @Body componentImage: UploadImage
//    ):Response<UploadImage>



}