package com.example.engineersguide

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.engineersguide.databinding.ActivityMainBinding
import com.example.engineersguide.databinding.FragmentWebBinding
import com.example.engineersguide.main.WebFragment
import com.example.engineersguide.repositories.SHARED_PREF_FILE
import com.google.firebase.database.FirebaseDatabase
import kotlin.system.measureNanoTime

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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


    // to disable back button completely
    override fun onBackPressed() {
        if ((supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController.currentDestination?.label == "fragment_web") {
            super.onBackPressed()
        } else {
            Toast.makeText(this, "This button has been disabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }


}