package com.example.engineersguide.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engineersguide.model.components.ComponentApi
import com.example.engineersguide.repositories.ApiServiceRepository
import com.google.firebase.components.Component
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

private const val TAG = "AddingComponentsViewMod"

class AddingComponentsViewModel : ViewModel() {


    private val apiRepo = ApiServiceRepository.get()


    val addedComponentLiveData = MutableLiveData<ComponentApi>()
    val addedComponentLiveError = MutableLiveData<String>()


    fun callComponents(
        title: String,
        descreption: String,
        functionality: String,
        equation: String,
        res1: String,
        res2: String,
        res3: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val component = ComponentApi(
                    "",
                    title,
                    descreption,
                    functionality,
                    equation,
                    "",
                    res1,
                    res2,
                    res3,
                    false
                )
                val response = apiRepo.addComponent(component)

                if (response.isSuccessful) {
                    response.body()?.run {
                        Log.d(TAG, this.toString())
                        addedComponentLiveData.postValue(this)
                        //  componentsErrorLiveData.postValue(response.message())

                    }
                } else {
                    Log.d(TAG, response.message())
                    addedComponentLiveError.postValue(response.message())
                }

            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())

                addedComponentLiveError.postValue(e.message.toString())

            }
        }
    }

//    fun uploadUserImage(file: File) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = apiRepo.uploadUserImage(file)
//
//                if (response.isSuccessful) {
//                    uploadImageLiveData.postValue("successful")
//                } else {
//                    Log.d(TAG,response.message())
//                    profileErrorsLiveData.postValue(response.message())
//                }
//            } catch (e: Exception) {
//                Log.d(TAG,e.message.toString())
//                profileErrorsLiveData.postValue(e.message.toString())
//            }
//        }
//    }


}