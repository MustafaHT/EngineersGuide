package com.example.engineersguide.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engineersguide.model.components.ComponentApi
import com.example.engineersguide.repositories.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "AddingComponentsViewMod"
class AddingComponentsViewModel:ViewModel() {


private val apiRepo = ApiServiceRepository.get()


    val addedComponent = MutableLiveData<ComponentApi>()
    val addedComponentLiveError = MutableLiveData<String>()



    fun callComponents(title:String,dec:String,func:String,equ:String,rec:String,rec2:String,rec3: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.addComponent(title,dec,func,equ,rec,rec2,rec3)

                if (response.isSuccessful) {
                    response.body()?.run {
                        Log.d(TAG, this.toString())
                        addedComponent.postValue(this)
                        //  componentsErrorLiveData.postValue(response.message())

                    }
                } else {
                    Log.d(TAG, response.message())
                    addedComponentLiveError.postValue(response.message())
                }

            } catch (e: Exception) {
                Log.d(TAG,e.message.toString())

                addedComponentLiveError.postValue(e.message.toString())
                /// componentsLiveData.postValue(databaseRepo.getComponents())
            }
        }
    }


}