package com.example.engineersguide.identity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engineersguide.api.ComponentsAPI
import com.example.engineersguide.model.components.Components
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
//import java.lang.Exception
//import kotlin.Exception


private const val TAG = "LoginViewModel"
class LoginViewModel:ViewModel() {



    val loginLiveData = MutableLiveData<List<Components>>()
    val loginErrorLiveData = MutableLiveData<String>()


    fun login(email:String , password:String){

        viewModelScope.launch(Dispatchers.IO){

            try {


            }catch (e:Exception){

            }
        }

    }

}