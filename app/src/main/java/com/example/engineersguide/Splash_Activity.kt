package com.example.engineersguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.engineersguide.databinding.ActivitySplashBinding
import com.example.engineersguide.repositories.ApiServiceRepository
import com.example.engineersguide.repositories.RoomServiceRepository
import java.util.zip.Inflater

class Splash_Activity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


    }
}