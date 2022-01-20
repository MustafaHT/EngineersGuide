package com.example.engineersguide.util

import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class BottomNavHelper(val bottomNavigation: BottomAppBar , val fab: FloatingActionButton) {

    fun showBar(){
        bottomNavigation.hideOnScroll = false
        bottomNavigation.performShow()
    }
    fun hideBar(){
        bottomNavigation.hideOnScroll = true
//        bottomNavigation.performHide()
    }
    fun performShowBar(){
        bottomNavigation.performShow()
    }
    fun performHideBar(){
        bottomNavigation.performHide()
    }
    fun unclickableFab(){
        fab.isEnabled = false
    }
    fun clikcableFab(){
        fab.isEnabled = true
    }
//    fun bottomHideOnScroll(){
//        bottomNavigation.hideOnScroll = true
//    }
    fun showFab(){
        fab.show()
    }
    fun hideFab(){
        fab.hide()
    }

    companion object{
        private var instance:BottomNavHelper? = null
        fun init(bottomAppBar: BottomAppBar, fab: FloatingActionButton){
            if(instance == null){
                instance = BottomNavHelper(bottomAppBar, fab)
            }
        }
        fun get():BottomNavHelper{
            return instance ?: throw Exception ("bottom nav helper is null")
        }
    }
}