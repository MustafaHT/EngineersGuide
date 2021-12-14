package com.example.engineersguide

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.engineersguide.repositories.SHARED_PREF_FILE
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

//    var sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
//    var sharedPrefEditor = sharedPref.edit()
//    sharedPrefEditor.putBoolean("a", true)
//    sharedPrefEditor.commit()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        

    }

    // to disable back button completely
    override fun onBackPressed() {
        Toast.makeText(this, "This button has been disabled", Toast.LENGTH_SHORT).show()
    }
}