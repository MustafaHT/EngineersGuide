package com.example.engineersguide.main

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

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

        binding.toggleButtonGroupSettings.addOnButtonCheckedListener{toggleButtonGroup,checkedId,isChecked ->
            if (isChecked){
                when(checkedId){
                    R.id.longView_ToggleButton -> {
                        showToast("long view has been selected")
                        }
                    R.id.square_ToggleButton -> {
                        showToast("squares view has been selected")
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


}