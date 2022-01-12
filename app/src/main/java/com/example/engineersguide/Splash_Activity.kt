package com.example.engineersguide

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.engineersguide.databinding.ActivitySplashBinding
import com.example.engineersguide.repositories.ApiServiceRepository
import com.example.engineersguide.repositories.RoomServiceRepository
import java.util.zip.Inflater

class Splash_Activity : AppCompatActivity() {


private val SHARED_PREF_FILE ="Auth"
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//            val sharedPref = this.getSharedPreferences(SHARED_PREF_FILE,MODE_PRIVATE)
//    val sharedPrefEditor = sharedPref.edit()
//    sharedPref.getBoolean("auth",true)
//    sharedPrefEditor.commit()


        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ApiServiceRepository.init(this)
        RoomServiceRepository.init(this)

        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            // here to let our splash move automatically...
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                val intent = Intent(this@Splash_Activity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }
        })


//        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
//            || ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
//            || ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(this,
//                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                    android.Manifest.permission.CAMERA,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE),0)
//        }


    }
}