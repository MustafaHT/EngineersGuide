package com.example.engineersguide.main

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.engineersguide.repositories.FirebaseRepository

class FirestorageViewModel:ViewModel() {

    private val uploadComonentLiveData = MutableLiveData<String>()
    private val uploadConponentErrorLiveData = MutableLiveData<String>()
    private val uploadProfileLiveData = MutableLiveData<String>()
    private val uploadProfileErrorLiveData = MutableLiveData<String>()

    private val fireStorageRepo= FirebaseRepository()


    fun uploadingComponentImage(uriS: Uri){
        fireStorageRepo.uploadComponentImage().putFile(uriS).addOnSuccessListener {
            uploadComonentLiveData.postValue("Successful")
        }.addOnFailureListener {
            uploadConponentErrorLiveData.postValue("Unsuccessful!")
        }
    }

    fun uploadingProfileImage(uriS: Uri){
        fireStorageRepo.uploadProfileImage().putFile(uriS).addOnSuccessListener {
            uploadProfileLiveData.postValue("Successful")
        }.addOnFailureListener {
            uploadProfileErrorLiveData.postValue("Unsuccessful!")
        }
        }
    }
