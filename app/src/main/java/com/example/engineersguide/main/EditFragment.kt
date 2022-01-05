package com.example.engineersguide.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
            it?.let { component ->

                binding.titleEditTextEditFragment.setText(component.componentTitle)
                binding.descreptionEditTextEditFragment.setText(component.description)
                binding.functionlityEditTextEditFragment.setText(component.functionality)
                binding.equationsEditTextEditFragment.setText(component.equations)
                binding.source1EditTextEditFragment.setText(component.source1)
                binding.source2EditTextEditFragment.setText(component.source2)
                binding.source3EditTextEditFragment.setText(component.source3)
                selectedComponent = component

            }
        })

        binding.saveComponentEditFragment.setOnClickListener {
            selectedComponent.componentTitle = binding.titleEditTextEditFragment.text.toString()
            selectedComponent.description = binding.descreptionEditTextEditFragment.text.toString()
            selectedComponent.functionality =
                binding.functionlityEditTextEditFragment.text.toString()
            selectedComponent.source1 = binding.source1EditTextEditFragment.text.toString()
            selectedComponent.source2 = binding.source2EditTextEditFragment.text.toString()
            selectedComponent.source3 = binding.source3EditTextEditFragment.text.toString()
            viewModel.updateComponent(selectedComponent.id.toInt(), selectedComponent)
            findNavController().navigate(R.id.action_editFragment_to_componentsFragment)

        }

        val titleLimit = 35
        binding.titleEditTextEditFragment.doOnTextChanged { text, start, before, count ->
            val titleLength = text?.length.toString()

            if (titleLimit - titleLength.toInt() == 0) {

                Toast.makeText(requireActivity(), "Title name reached its limit", Toast.LENGTH_LONG)
                    .show()
            }
            binding.titleLengthTextViewEditFragment.text =
                (titleLimit - titleLength.toInt()).toString()

        }
    }
}

