package com.example.engineersguide.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.engineersguide.R
import com.example.engineersguide.adapters.ComponentsRecyclerViewAdapter
import com.example.engineersguide.api.ComponentsAPI
import com.example.engineersguide.databinding.FragmentAddingComponentsBinding
import com.example.engineersguide.databinding.FragmentDetailsBinding
import com.example.engineersguide.model.components.ComponentApi

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var selectedComponent:ComponentApi
    private val viewModel: ComponentsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedComponent.observe(viewLifecycleOwner, Observer {
            it?.let { component ->
                binding.titleTextViewDetails.text = component.componentTitle
                binding.descreptionTextViewDetails.text = component.description
                binding.functionalityTextViewDetails.text = component.functionality
                binding.equationsTextViewDetails.text = component.equations
                binding.source1TextViewDetails.text = component.source1
                binding.source2TextViewDetails.text = component.source2
                binding.source3TextViewDetails.text = component.source3
                selectedComponent = component


                if (binding.descreptionTextViewDetails.text.length >= 1000){
                        binding.viewDescreptionDetails.visibility = View.VISIBLE
                        binding.descreptionTextViewDetails.text = binding.descreptionTextViewDetails.text.substring(0 , 900 - 3) + "..."
                        binding.viewDescreptionDetails.setOnClickListener {
                            if (binding.viewDescreptionDetails.text == "view more"){
                                binding.descreptionTextViewDetails.text = component.description
                                binding.viewDescreptionDetails.text = "view less"
                            }else{
                                binding.descreptionTextViewDetails.text = binding.descreptionTextViewDetails.text.substring(0 , 900 - 3) + "..."
                                binding.viewDescreptionDetails.text = "view more"
                            }
                        }
                    }

            }
        })



        binding.imageButtonForSource1.setOnClickListener {
            Bundle().apply {
                putString("link", selectedComponent.source1)
                it.findNavController().navigate(R.id.action_detailsFragment_to_webFragment, this)
            }

        }
        binding.imageButtonForSource2.setOnClickListener {
            Bundle().apply {
                putString("link", selectedComponent.source2)
                it.findNavController().navigate(R.id.action_detailsFragment_to_webFragment, this)
            }
        }
        binding.imageButtonForSource3.setOnClickListener {
            Bundle().apply {
                putString("link", selectedComponent.source3)
                it.findNavController().navigate(R.id.action_detailsFragment_to_webFragment, this)
            }
        }
    }
}