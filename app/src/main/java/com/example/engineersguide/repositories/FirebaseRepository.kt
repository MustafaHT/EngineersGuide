package com.example.engineersguide.repositories


import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import com.example.engineersguide.datamodels.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class FirebaseRepository() {


    private val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
    val name = formatter.format(Date())
    private var Reference =  Firebase.firestore.collection("Users")
    private var storageReference = FirebaseStorage.getInstance().getReference()
fun uploadComponentImage() = FirebaseStorage.getInstance().getReference("componentsPictures/$name")

    fun uploadProfileImage() = FirebaseStorage.getInstance().getReference("profilePicture/${FirebaseAuth.getInstance().uid}")


    fun save(userInfo: User) = Reference.document(FirebaseAuth.getInstance().uid.toString()).set(userInfo)
//    fun UploadPhoto(image: Uri) = storageReference.child(FirebaseAuth.getInstance().uid.toString()).putFile(image)
    fun getUser()  = Reference.document(FirebaseAuth.getInstance().uid.toString()).get()
    fun getUserById(userId: String) = Reference.document(userId).get()


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: FirebaseRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = FirebaseRepository()
        }

        fun get(): FirebaseRepository {
            return instance ?: throw Exception("ApiServiceRepository must be initialized ")
        }

    }

}