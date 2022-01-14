package com.example.engineersguide

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationBuilderWithBuilderAccessor
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.engineersguide.databinding.ActivityMainBinding
import com.example.engineersguide.databinding.FragmentWebBinding
import com.example.engineersguide.main.FirestorageViewModel
import com.example.engineersguide.main.WebFragment
import com.example.engineersguide.repositories.SHARED_PREF_FILE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.itextpdf.text.pdf.PRIndirectReference
import kotlin.system.measureNanoTime

val CHANNEL_ID = "channel_id_example_01"
val notificationId = 101



class MainActivity : AppCompatActivity() {

//    var sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
//    var sharedPrefEditor = sharedPref.edit()
//    sharedPrefEditor.putBoolean("a", true)
//    sharedPrefEditor.commit()

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()
        Notification()


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)






    }

    @SuppressLint("RestrictedApi")
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descreptionText = "Notification Descreption"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                var descreption = descreptionText
            }
            val notificationManger: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManger.createNotificationChannel(channel)
        }
    }

    private fun Notification(){
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setContentTitle("Engineers Guide")
            .setContentText("Welcome To The Lighted Road")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


    // to disable back button completely
    override fun onBackPressed() {

        when((supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController.currentDestination?.label){
            "Browser" -> super.onBackPressed()

            "Edit" -> super.onBackPressed()

            "Details" -> super.onBackPressed()

            "Add Component" -> super.onBackPressed()

            else -> Toast.makeText(this, "This button has been disabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }



}