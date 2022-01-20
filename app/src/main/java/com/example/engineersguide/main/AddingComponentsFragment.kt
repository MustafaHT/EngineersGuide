package com.example.engineersguide.main

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.engineersguide.databinding.FragmentAddingComponentsBinding
import com.example.engineersguide.identity.ProfileViewModel
import com.example.engineersguide.model.components.ComponentModel
import com.example.engineersguide.repositories.ApiServiceRepository
import com.example.engineersguide.repositories.FirebaseRepository
import com.example.engineersguide.util.BottomNavHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy


private lateinit var apiServiceRepository: ApiServiceRepository
private const val TAG = "AddingComponentsFragment"

class AddingComponentsFragment : Fragment() {

    private var user = com.example.engineersguide.datamodels.User()

    private val IMAGE_PICKER = 0
    private var image: Uri? = null

    private lateinit var binding: FragmentAddingComponentsBinding

    private val addingComponentsViewModel = AddingComponentsViewModel()
    private val firestorageViewModel = FirestorageViewModel()

    val fireStorageRepo = FirebaseRepository()

    private lateinit var selectedItem: ComponentModel

    private lateinit var progressDialog: ProgressDialog
    private val addList = mutableListOf<ComponentModel>()
    private var mStorage: StorageReference? = null

    private val viewModel: ComponentsViewModel by activityViewModels()

    private val profileViewModel = ProfileViewModel()




    private var imageButtonNum = 0
    private lateinit var imageView:String

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
        BottomNavHelper.get().performHideBar()
        BottomNavHelper.get().hideFab()

        observer()

        mStorage = FirebaseStorage.getInstance().reference

        binding.componentImageButton.setOnClickListener() {
            imageButtonNum = 0
            showImagePicker()
            imageButtonNum++
//            val intentImage = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            intentImage.type = "image/*"
//                activity?.startActivityForResult(intentImage, 2)
//            Toast.makeText(context, "This button is disabled", Toast.LENGTH_LONG).show()

        }




        binding.addComponent.setOnClickListener {
//            viewModel.selectedComponent.observe(viewLifecycleOwner, {
//                it?.let { component ->



                    val title = binding.titleEditText.text.toString()
                    if (imageButtonNum == 0) {
                         imageView =
                            ""
                    }else{
                         imageView = "https://firebasestorage.googleapis.com/v0/b/engineers-guide-bdbaa.appspot.com/o/componentsPictures%2F${fireStorageRepo.name}?alt=media&token=1ba24a8a-2794-4849-ba3f-14dfd27e8f16"
                    }
                    val descreption = binding.descreptionEditText.text.toString()
                    val functionality = binding.functionlityEditText.text.toString()
                    val equation = binding.equationsEditText.text.toString()
                    val source1 = binding.source1EditText.text.toString()
                    val source2 = binding.source2EditText.text.toString()
                    val source3 = binding.source3EditText.text.toString()
                    val username = FirebaseAuth.getInstance().currentUser?.email.toString()

                    Log.d(TAG, "add Component Button")
                    addingComponentsViewModel.callComponents(
                        title,
                        imageView,
                        descreption,
                        functionality,
                        equation,
                        source1,
                        source2,
                        source3,
                        username
                    )
//                }


//            })
            viewModel.callComponents()
            image?.let { it1 ->
                firestorageViewModel.uploadingComponentImage(it1) }
            findNavController().popBackStack()

        }

        //=============================================================================================================

        // giving a limit for of to the title
        val titleLimit = 25
        binding.titleEditText.doOnTextChanged { text, start, before, count ->
            val titleLength = text?.length.toString()

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


        if (requestCode == IMAGE_PICKER && resultCode == RESULT_OK) {
            //here this code used to show the image that the user selected inside the components imageView
            image = Matisse.obtainResult(data)[0]
            Glide.with(requireContext()).load(image).into(binding.ComponentImageView)
            // instead of using binding.ComponentImageView.setImageURI(data?.data)
            // I used Glide because setImageURI make the image that have been selected zoomed inside
            // the imageView ...
            if (image.toString().length > 4) {
                imageButtonNum++
                Log.d(TAG, imageButtonNum.toString())
                Log.d(TAG, "inside onActivityResult")
            }
            Log.d(TAG, image.toString().length.toString())

        }


//
//        val imageFile = File(imagePath)
//
//        addingComponentsViewModel.

//        Log.d(TAG, "BEFORE CONDITION")
//        if (requestCode == 2 && resultCode == RESULT_OK) {
//            val uriImage = data?.data
//            Log.d(TAG, "IMAGE")
//
//            val filePath = mStorage?.child(Calendar.getInstance().time.toString())
//            Log.d(TAG, "file path: $filePath")
//            if (uriImage != null) {
//                Log.d(TAG, " URI Image: $uriImage")
//                filePath?.putFile(uriImage)?.addOnSuccessListener {
//                    Log.d(TAG, "Successfully added")
//
//                    Toast.makeText(context, "Upload Image", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

    @SuppressLint("LongLogTag")
    fun showImagePicker() {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .capture(true)
            .captureStrategy(CaptureStrategy(true, "com.example.engineersguide"))
            .forResult(IMAGE_PICKER)

        if (image.toString().length == 4) {
            imageButtonNum--
            Log.d(TAG, "inside showImage")

//        if (MimeType == null){
//            imageButton
//        }
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

//                Glide.with(requireContext()).load("").into(binding.ComponentImageView)
                }
            })
            addingComponentsViewModel.addedComponentLiveError.observe(viewLifecycleOwner, {
                Log.d(TAG, "observer errorLiveData")
                progressDialog.dismiss()
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            })
        }


    }
