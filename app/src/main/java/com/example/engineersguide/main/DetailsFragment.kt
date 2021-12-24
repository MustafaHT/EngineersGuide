package com.example.engineersguide.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedComponent.observe(viewLifecycleOwner, Observer {
            it?.let {component ->
                binding.titleTextViewDetails.text = component.componentTitle
                binding.descreptionTextViewDetails.text = component.description
                binding.functionalityTextViewDetails.text = component.functionality
                binding.equationsTextViewDetails.text = component.equations
                binding.source1TextViewDetails.text = component.source1
                binding.source2TextViewDetails.text = component.source2
                binding.source3TextViewDetails.text = component.source3
            }
        })

        binding.imageButtonForSource1.setOnClickListener {
            it.findNavController().navigate(R.id.action_detailsFragment_to_webFragment)
        }
    }
}