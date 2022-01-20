package com.example.engineersguide.main

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.preference.Preference
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentSettingsBinding
import com.google.android.material.button.MaterialButtonToggleGroup


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding


    val SHARED_PREF_FILE = "toggleButton"
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_FILE, MODE_PRIVATE)

//        sharedPref.getBoolean("toggleButton",binding.btnCenter.isSelected)
        sharedPref.getBoolean("toggleButton",true)

        binding.toggleButtonGroupSettings.addOnButtonCheckedListener{toggleButtonGroup,checkedId,isChecked ->
            if (isChecked){
                sharedPrefEditor = sharedPref.edit()
//                sharedPrefEditor.putBoolean("toggleButton", true)
//                sharedPrefEditor.commit()



                when(checkedId){
                    R.id.btnCenter -> {
                        showToast("long view has been selected")
                        sharedPrefEditor.putBoolean("toggleButton", false)
                        sharedPrefEditor.commit()
                        }
                    R.id.btnLeft -> {
                        showToast("squares view has been selected")
                        sharedPrefEditor.putBoolean("toggleButton", true)
                        sharedPrefEditor.commit()
                    }
                }
            }else{
                if (toggleButtonGroup.checkedButtonId == View.NO_ID){
                    showToast("No view selected")
                }
            }



        }







    }

    private fun showToast(str:String){
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

//    private fun savedData(){
//        val sharedPref: SharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_FILE,
//            MODE_PRIVATE)
//        val editor:SharedPreferences.Editor = sharedPref.edit()
//
//        view?.findViewById<ToggleButton>(R.id.toggleButtonGroup_settings)?.let {
//            editor.putBoolean("toggleButton",
//                it.isChecked)
//        }
//
//    }
//
//    private fun loadData(){
//       val sharedPref: SharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_FILE,
//        MODE_PRIVATE)
//
//        binding.toggleButtonGroupSettings.isSingleSelection = sharedPref.getBoolean("toggleButton",binding.btnCenter.isPressed)
//    }

//    private fun update(){
//        binding.toggleButtonGroupSettings.set
//    }



}