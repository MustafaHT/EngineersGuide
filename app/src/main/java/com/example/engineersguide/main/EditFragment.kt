package com.example.engineersguide.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentEditBinding
import com.example.engineersguide.model.components.ComponentApi


class EditFragment : Fragment() {

    private lateinit var binding:FragmentEditBinding

    private val viewModel: ComponentsViewModel by activityViewModels()
    private lateinit var selectedComponent: ComponentApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.selectedComponent.observe(viewLifecycleOwner, Observer {
            it?.let {component ->

                binding.titleEditTextEditFragment.setText(component.componentTitle)
                binding.descreptionEditTextEditFragment.setText(component.description)
                binding.functionlityEditTextEditFragment.setText(component.functionality)
                binding.equationsEditTextEditFragment.setText(component.equations)
                binding.source1EditTextEditFragment.setText(component.source1)
                binding.source2EditTextEditFragment.setText(component.source2)
                binding .source3EditTextEditFragment.setText(component.source3)

            }
        })


    }
}