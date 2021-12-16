package com.example.engineersguide.identity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engineersguide.model.components.ComponentApi
import com.example.engineersguide.repositories.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//import java.lang.Exception
//import kotlin.Exception


private const val TAG = "LoginViewModel"
class LoginViewModel:ViewModel() {

    private val apiRepo = ApiServiceRepository.get()

    val loginLiveData = MutableLiveData<List<ComponentApi>>()
    val loginErrorLiveData = MutableLiveData<String>()


    fun login(email:String , password:String){

        viewModelScope.launch(Dispatchers.IO) {

            try {
//                val response = apiRepo

            }catch (e:Exception){

            }
        }

    }

}