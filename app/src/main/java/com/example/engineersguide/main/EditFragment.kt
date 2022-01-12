package com.example.engineersguide.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentEditBinding
import com.example.engineersguide.model.components.ComponentModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy

private const val TAG = "EditFragment"
class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding

    private var image: Uri? = null

    private val IMAGE_PICKER = 0

    private val firestorageViewModel = FirestorageViewModel()
    private val viewModel: ComponentsViewModel by activityViewModels()
    private lateinit var selectedComponent: ComponentModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.componentImageButtonEditFragment.setOnClickListener {
            showImagePicker()
        }



        viewModel.selectedComponent.observe(viewLifecycleOwner, Observer {
            it?.let { component ->

                binding.titleEditTextEditFragment.setText(component.componentName)
                binding.descreptionEditTextEditFragment.setText(component.description)
                val pic = Glide.with(requireContext()).load(component.componentImageUrl)
                    .into(binding.ComponentImageViewEditFragment)
                binding.functionlityEditTextEditFragment.setText(component.functionality)
                binding.equationsEditTextEditFragment.setText(component.equations)
                binding.source1EditTextEditFragment.setText(component.source1)
                binding.source2EditTextEditFragment.setText(component.source2)
                binding.source3EditTextEditFragment.setText(component.source3)
                selectedComponent = component

            }
        })

        binding.saveComponentEditFragment.setOnClickListener {
            selectedComponent.componentName = binding.titleEditTextEditFragment.text.toString()
            selectedComponent.componentImageUrl = Glide.with(requireContext()).load(pic).into(binding.ComponentImageViewEditFragment).toString()
            selectedComponent.description = binding.descreptionEditTextEditFragment.text.toString()
            selectedComponent.functionality =
                binding.functionlityEditTextEditFragment.text.toString()
            selectedComponent.source1 = binding.source1EditTextEditFragment.text.toString()
            selectedComponent.source2 = binding.source2EditTextEditFragment.text.toString()
            selectedComponent.source3 = binding.source3EditTextEditFragment.text.toString()
            image?.let { it1 -> firestorageViewModel.uploadingComponentImage(it1) }
            viewModel.updateComponent(selectedComponent.id.toInt(), selectedComponent)
            findNavController().navigate(R.id.action_editFragment_to_componentsFragment)

        }

        val titleLimit = 25
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

    fun showImagePicker() {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .capture(true)
            .captureStrategy(CaptureStrategy(true, "com.example.engineersguide"))
            .forResult(IMAGE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICKER && resultCode == Activity.RESULT_OK) {
            //here this code used to show the image that the user selected inside the components imageView
            Log.d(TAG, "Why? why?")
            image = Matisse.obtainResult(data)[0]
            Glide.with(requireContext()).load(image).into(binding.ComponentImageViewEditFragment)
        }
    }
}

