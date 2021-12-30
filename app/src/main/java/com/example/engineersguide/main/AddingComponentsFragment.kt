package com.example.engineersguide.main

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentAddingComponentsBinding
import com.example.engineersguide.model.components.ComponentApi
import com.example.engineersguide.repositories.ApiServiceRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


private lateinit var apiServiceRepository: ApiServiceRepository
private const val TAG = "AddingComponentsFragment"

class AddingComponentsFragment : Fragment() {

    private lateinit var binding: FragmentAddingComponentsBinding

    private val addingComponentsViewModel = AddingComponentsViewModel()

    private lateinit var progressDialog: ProgressDialog
    val addList = mutableListOf<ComponentApi>()
    var mStorage: StorageReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)
        // Inflate the layout for this fragment
        binding = FragmentAddingComponentsBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        mStorage = FirebaseStorage.getInstance().reference

        binding.componentImageButton.setOnClickListener() {
//            val intentImage = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
////            intentImage.type = "image/*"
//                activity?.startActivityForResult(intentImage, 2)
            Toast.makeText(context, "This button is disabled", Toast.LENGTH_LONG).show()

        }


        binding.addComponent.setOnClickListener() {
            val title = binding.titleEditText.text.toString()
            val descreption = binding.descreptionEditText.text.toString()
            val functionality = binding.functionlityEditText.text.toString()
            val equation = binding.equationsEditText.text.toString()
            val source1 = binding.source1EditText.text.toString()
            val source2 = binding.source2EditText.text.toString()
            val source3 = binding.source3EditText.text.toString()
            Log.d(TAG, "add Component Button")
            addingComponentsViewModel.callComponents(
                title,
                descreption,
                functionality,
                equation,
                source1,
                source2,
                source3
            )

            findNavController().navigate(R.id.action_addingComponentsFragment_to_componentsFragment)

        }

        //=============================================================================================================

        // giving a limit for of to the title
        val titleLimit = 35
        binding.titleEditText.doOnTextChanged { text, start, before, count ->
            var titleLength = text?.length.toString()

            if (titleLimit - titleLength.toInt() == 0) {

                Toast.makeText(requireActivity(), "Title name reached its limit", Toast.LENGTH_LONG)
                    .show()
            }
            binding.titleLengthTextView.text = (titleLimit - titleLength.toInt()).toString()

        }
        //============================================================================================================


        Log.d(TAG, mStorage.toString())



        apiServiceRepository = ApiServiceRepository(requireContext())

    }


    @SuppressLint("LongLogTag")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "BEFORE CONDITION")
        if (requestCode == 2 && resultCode == RESULT_OK) {
            val uriImage = data?.data
            Log.d(TAG, "IMAGE")

            val filePath = mStorage?.child(Calendar.getInstance().time.toString())
            Log.d(TAG, "file path: $filePath")
            if (uriImage != null) {
                Log.d(TAG, " URI Image: $uriImage")
                filePath?.putFile(uriImage)?.addOnSuccessListener {
                    Log.d(TAG, "Successfully added")

                    Toast.makeText(context, "Upload Image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun observer() {
        addingComponentsViewModel.addedComponentLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Log.d(TAG, "observer liveData")
                addList.add(it)
                Log.d("here", it.toString())
                progressDialog.dismiss()
                findNavController().popBackStack()
                addingComponentsViewModel.addedComponentLiveData.postValue(null)
            }
        })
        addingComponentsViewModel.addedComponentLiveError.observe(viewLifecycleOwner, {
            Log.d(TAG, "observer errorLiveData")
            progressDialog.dismiss()
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
    }
}