package com.example.engineersguide.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engineersguide.model.components.Components
import com.example.engineersguide.repositories.ApiServiceRepository
//import com.example.engineersguide.repositories.RoomServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "ComponentsViewModel"

class ComponentsViewModel : ViewModel() {

//    private val apiRepo = ApiServiceRepository.get()

//    private val databaseRepo = RoomServiceRepository.get()


    val componentsLiveData = MutableLiveData<List<Components>>()
    val componentsErrorLiveData = MutableLiveData<String>()

//    fun callComponents() {
//
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = apiRepo.getComponents()
//
//                if (response.isSuccessful) {
//                    response.body()?.run {
//                        Log.d(TAG, this.toString())
//
////                        componentsLiveData.postValue(components)
////                        componentsErrorLiveData.postValue(components)
//
//                    }
//                } else {
//                    Log.d(TAG, response.message())
//                    componentsErrorLiveData.postValue(response.message())
////                    componentsLiveData.postValue(databaseRepo.getComponents())
//                }
//
//            } catch (e: Exception) {
//                    Log.d(TAG,e.message.toString())
//
//                componentsErrorLiveData.postValue(e.message.toString())
////                componentsLiveData.postValue(databaseRepo.getComponents())
//            }
//        }
    }

//}