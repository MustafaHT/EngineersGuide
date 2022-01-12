package com.example.engineersguide.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.example.engineersguide.model.components.ComponentModel
import com.example.engineersguide.repositories.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "AddingComponentsViewMod"

class AddingComponentsViewModel : ViewModel() {
    private val urlS = ""

    private val apiRepo = ApiServiceRepository.get()


    val addedComponentLiveData = MutableLiveData<ComponentModel>()
    val addedComponentLiveError = MutableLiveData<String>()



    fun callComponents(
        title: String,
        imageView: String,
        descreption: String,
        functionality: String,
        equation: String,
        res1: String,
        res2: String,
        res3: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val component = ComponentModel(
                    imageView,
                    title,
                    descreption,
                    functionality,
                    equation,
                    "",
                    false,
                    res1,
                    res2,
                    res3
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

}