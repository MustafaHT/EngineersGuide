package com.example.engineersguide.identity

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engineersguide.datamodels.User
import com.example.engineersguide.repositories.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ProfileViewModel"
class ProfileViewModel:ViewModel() {

    val saveUsersLiveDate = MutableLiveData<User>()
    val UploadProfileLiveDate = MutableLiveData<Uri>()
    val getUserLiveDate = MutableLiveData<User>()
    val usersErrorLiveData = MutableLiveData<String>()
    val getlistUserLiveData = MutableLiveData<List<User>>()

    private val firebaseRepository = FirebaseRepository.get()
    private var firestore: FirebaseFirestore


    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

    }

    //------------------------------------------------save and edit users
    fun save(userInfo: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firebaseRepository.save(userInfo).addOnSuccessListener {
                    Log.d(TAG, "saved")
                    saveUsersLiveDate.postValue(userInfo)
                }.addOnFailureListener {
                    usersErrorLiveData.postValue(it.message.toString())
                    Log.d(TAG, it.message.toString())
                }

            } catch (e: Exception) {
                Log.d(ContentValues.TAG, e.message.toString())
                usersErrorLiveData.postValue(e.message.toString())
            }
        }
    }

//    fun UploadPhoto(imge: Uri) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val responseImage = firebaseRepository.UploadPhoto(imge)
//                responseImage.addOnSuccessListener { taskSnapshot ->
//                    Log.d(TAG, taskSnapshot.metadata?.name.toString())
//                    UploadPhotosersLiveDate.postValue(imge)
//
//                }.addOnFailureListener {
//                    Log.d(TAG, it.message.toString())
//                    usersErrorLiveData.postValue(it.message.toString())
//                }
//
//            } catch (e: Exception) {
//                Log.d(ContentValues.TAG, e.message.toString())
//                usersErrorLiveData.postValue(e.message.toString())
//            }
//        }
//    }

//    fun UploadProfilePhoto(image: Uri) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val responseImage = firebaseRepository.UploadPhoto(image)
//                responseImage.addOnSuccessListener { taskSnapshot ->
//                    Log.d(TAG, taskSnapshot.metadata?.name.toString())
//                    UploadPhotosersLiveDate.postValue(image)
//
//                }.addOnFailureListener {
//                    Log.d(TAG, it.message.toString())
//                    usersErrorLiveData.postValue(it.message.toString())
//                }
//
//            } catch (e: Exception) {
//                Log.d(ContentValues.TAG, e.message.toString())
//                usersErrorLiveData.postValue(e.message.toString())
//            }
//        }
//    }

//    fun uploadPrfilePicture(){
//        firebaseRepository.uploadProfileImage().
//    }

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG,"user Id:"+FirebaseAuth.getInstance().uid.toString())
                firebaseRepository.getUser().addOnSuccessListener { user1 ->
                    Log.d(TAG,"$user1")
                    val user = user1.toObject(User::class.java)
                    Log.d(TAG,"$user")
                    getUserLiveDate.postValue(user!!)
                    Log.d(TAG, "")
                }.addOnFailureListener {
                    usersErrorLiveData.postValue(it.message.toString())
                    Log.d(TAG, it.message.toString())
                }
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, e.message.toString())
                usersErrorLiveData.postValue(e.message.toString())
            }
        }
    }
}