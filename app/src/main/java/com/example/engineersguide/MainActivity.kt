package com.example.engineersguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        

    }

    // to disable back button completely
    override fun onBackPressed() {
        Toast.makeText(this, "This button has been disabled", Toast.LENGTH_SHORT).show()
    }
}