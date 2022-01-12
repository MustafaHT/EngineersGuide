package com.example.engineersguide.repositories


import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter




class FirebaseRepository() {


    private val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
    val name = formatter.format(Date())
fun uploadComponentImage() = FirebaseStorage.getInstance().getReference("componentsPictures/$name")

}