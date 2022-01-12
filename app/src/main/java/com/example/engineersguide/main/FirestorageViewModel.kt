package com.example.engineersguide.main

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.engineersguide.repositories.FirebaseRepository

class FirestorageViewModel:ViewModel() {

    private val uploadComonentLiveData = MutableLiveData<String>()
    private val uploadConponentErrorLiveData = MutableLiveData<String>()

    private val fireStorageRepo = FirebaseRepository()


    fun uploadingComponentImage(uriS:Uri){
        fireStorageRepo.uploadComponentImage().putFile(uriS).addOnSuccessListener {
            uploadComonentLiveData.postValue("Successful")
        }.addOnFailureListener {
            uploadConponentErrorLiveData.postValue("Unsuccessful!")
        }
    }

}