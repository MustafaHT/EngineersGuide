package com.example.engineersguide.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engineersguide.model.components.ComponentModel
import com.example.engineersguide.repositories.ApiServiceRepository
import com.example.engineersguide.repositories.RoomServiceRepository
import kotlinx.coroutines.launch


private const val TAG = "ComponentsViewModel"


class ComponentsViewModel : ViewModel() {

    private val apiRepo = ApiServiceRepository.get()

    private val databaseRepo = RoomServiceRepository.get()

    val selectedComponent = MutableLiveData<ComponentModel>()
    val deletedItemResponseLiveData = MutableLiveData<String>()
    val deletedItemErrorLiveData = MutableLiveData<String>()
    val componentsLiveData = MutableLiveData<List<ComponentModel>>()
    val componentsErrorLiveData = MutableLiveData<String>()



    fun callComponents() {
        viewModelScope.launch {
            try {
                val response = apiRepo.getComponents()
                if (response.isSuccessful) {
                    response.body()?.run {

                        componentsLiveData.postValue(this)
                        databaseRepo.insertComponents(this)

                    }
                } else {
                    componentsErrorLiveData.postValue(response.message())
                }
            } catch (e: Exception) {
                componentsErrorLiveData.postValue(e.message.toString())
            }
        }

    }

    fun deleteComponent(componentsId: Int){
        viewModelScope.launch {
            try {
                val response = apiRepo.deleteComponent(componentsId)
                if (response.isSuccessful){
                    response.body()?.run {
                        deletedItemResponseLiveData.postValue("Deleted Successfully!")
                    }
                }else{
                    deletedItemErrorLiveData.postValue(response.message())
                }
            }catch (e:Exception){
                deletedItemErrorLiveData.postValue(e.message.toString())
            }

        }
    }

    fun updateComponent(componentApi: Int,component: ComponentModel){
        viewModelScope.launch {
            apiRepo.updataComponent(componentApi,component)
        }
    }


}