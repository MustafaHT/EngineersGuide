package com.example.engineersguide.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engineersguide.model.components.ComponentApi
import com.example.engineersguide.repositories.ApiServiceRepository
import com.example.engineersguide.repositories.ApiServiceRepository.Companion.get
import com.example.engineersguide.repositories.RoomServiceRepository
import com.example.engineersguide.repositories.RoomServiceRepository.Companion.get
import kotlinx.coroutines.launch


private const val TAG = "ComponentsViewModel"


class ComponentsViewModel : ViewModel() {

    private val apiRepo = ApiServiceRepository.get()

    private val databaseRepo = RoomServiceRepository.get()

    val selectedComponent = MutableLiveData<ComponentApi>()

    val componentsLiveData = MutableLiveData<List<ComponentApi>>()
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


}