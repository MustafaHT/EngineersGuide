package com.example.engineersguide.main

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.MimeTypeFilter
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentEditBinding
import com.example.engineersguide.model.components.ComponentModel
import com.example.engineersguide.repositories.FirebaseRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.itextpdf.text.Jpeg
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy

private const val TAG = "EditFragment"

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding

    private var image: Uri? = null

    private val IMAGE_PICKER = 0

    // this will be used whenever we press the image button the number will increase and this will help inside if condition
    private var imageButtonNum = 0


    private val firestorageViewModel = FirestorageViewModel()
    private val fireStorageRepo = FirebaseRepository()
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

        Log.d(TAG, imageButtonNum.toString())

        binding.componentImageButtonEditFragment.setOnClickListener {
            imageButtonNum = 0
            showImagePicker()
            imageButtonNum++
            Log.d(TAG, imageButtonNum.toString())
        }

        viewModel.selectedComponent.observe(viewLifecycleOwner, Observer {
            it?.let { component ->

                binding.titleEditTextEditFragment.setText(component.componentName)
                binding.descreptionEditTextEditFragment.setText(component.description)
                binding.functionlityEditTextEditFragment.setText(component.functionality)
                binding.equationsEditTextEditFragment.setText(component.equations)
                binding.source1EditTextEditFragment.setText(component.source1)
                binding.source2EditTextEditFragment.setText(component.source2)
                binding.source3EditTextEditFragment.setText(component.source3)
                selectedComponent = component
                Glide.with(requireContext()).load(selectedComponent.componentImageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(binding.ComponentImageViewEditFragment)

            }
        })
        binding.saveComponentEditFragment.setOnClickListener {
            selectedComponent.componentName = binding.titleEditTextEditFragment.text.toString()

            // here this condition used to check if the user chosed a pic or NOT if he choosed a pic then the imageButtonNum will increase by 1 other wise will stay at 0
            // 1 means that he choosed a pic
            // 0 means the he hasn't changed it
            if (imageButtonNum == 0) {
                Log.d(TAG, imageButtonNum.toString())
                selectedComponent.componentImageUrl
                Log.d(TAG, "inside if")
                Log.d(TAG,imageButtonNum.toString())
            } else {
                Log.d(TAG, imageButtonNum.toString())
                Log.d(TAG, "inside else")
                selectedComponent.componentImageUrl =
                    "https://firebasestorage.googleapis.com/v0/b/engineers-guide-bdbaa.appspot.com/o/componentsPictures%2F${fireStorageRepo.name}?alt=media&token=1ba24a8a-2794-4849-ba3f-14dfd27e8f16"
            }
            selectedComponent.description = binding.descreptionEditTextEditFragment.text.toString()
            selectedComponent.functionality =
                binding.functionlityEditTextEditFragment.text.toString()
            selectedComponent.source1 = binding.source1EditTextEditFragment.text.toString()
            selectedComponent.source2 = binding.source2EditTextEditFragment.text.toString()
            selectedComponent.source3 = binding.source3EditTextEditFragment.text.toString()
            image?.let { it1 -> firestorageViewModel.uploadingComponentImage(it1) }
            viewModel.updateComponent(selectedComponent.id.toInt(), selectedComponent)
            findNavController().navigate(R.id.action_editFragment_to_componentsFragment)
            imageButtonNum = 0
        }
        // here i used this code to give the title a limited length or limited number of characters that the user can use in the title editTextView
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

    private fun showImagePicker() {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .capture(false)
            .captureStrategy(CaptureStrategy(true, "com.example.engineersguide"))
            .forResult(IMAGE_PICKER)
        // this condition is to make sure that the imageButtonNum stays 0 when the user does NOT choose any Pic
        if (image.toString().length == 4){
            imageButtonNum--
            Log.d(TAG,"inside showImage")
        }
        Log.d(TAG,"image"+image.toString().length.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICKER && resultCode == Activity.RESULT_OK) {
            //here this code used to show the image that the user selected inside the components imageView
            Log.d(TAG, "Why? why?")
            image = Matisse.obtainResult(data)[0]
            // And in this condition if the user choosed any pic the length of the image will increase for this reason I used the length to know if the user change the pic or not
            // other wise the pic will stay the same if we return to the first condition above
            if (image.toString().length > 4){
                imageButtonNum++
                Log.d(TAG,imageButtonNum.toString())
                Log.d(TAG,"inside onActivityResult")
            }
            Log.d(TAG,image.toString().length.toString())
            Glide.with(requireContext()).load(image).into(binding.ComponentImageViewEditFragment)
        }
    }
}

