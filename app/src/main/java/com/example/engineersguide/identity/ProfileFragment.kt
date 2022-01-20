package com.example.engineersguide.identity

import android.annotation.SuppressLint
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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentProfileBinding
import com.example.engineersguide.datamodels.User
import com.example.engineersguide.main.FirestorageViewModel
import com.example.engineersguide.model.components.ComponentModel
import com.example.engineersguide.repositories.FirebaseRepository
import com.example.engineersguide.util.BottomNavHelper
import com.google.firebase.auth.FirebaseAuth
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
private const val TAG = "ProfileFragment"
class ProfileFragment : Fragment() {
    private val IMAGE_PICKER = 0
    private  var user:User? = null
    private lateinit var selectedItem: ComponentModel
    private val viewModel: ProfileViewModel by activityViewModels()
    var imagePath: Uri = "".toUri()

    private val fireStorageRepo = FirebaseRepository()
    private val firestorageViewModel = FirestorageViewModel()


    private lateinit var binding: FragmentProfileBinding

    private val image = "https://firebasestorage.googleapis.com/v0/b/engineers-guide-bdbaa.appspot.com/o/profilePicture%2F${FirebaseAuth.getInstance().uid}?alt=media&token=7f36276f-f5a4-4c36-8f73-7222cadc676c"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButtonProfile.isEnabled = false
        observer()
        BottomNavHelper.get().unclickableFab()
        BottomNavHelper.get().hideFab()




        binding.profilePhoto.setOnClickListener {
            ImagePicker()
        }

        binding.saveButtonProfile.setOnClickListener {
            binding.saveButtonProfile.isEnabled = false
            saveEdite()
            viewModel.getProfile()
            imagePath.let {
                firestorageViewModel.uploadingProfileImage(imagePath)
            }
            Toast.makeText(requireContext(), "all changes have been saved", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        binding.firstNameProfileEditText.doOnTextChanged { text, start, before, count ->
            user?.let {
                if (text.toString().lowercase() != it.fName.toString().lowercase()) {
                    Log.d(TAG, "data class: ${it.fName}")
                    Log.d(TAG, "edit text: $text")
                    binding.saveButtonProfile.isEnabled = true
                } else {
                    binding.saveButtonProfile.isEnabled = false
                }
            }
        }

        binding.lastNameProfileEditText.doOnTextChanged { text, start, before, count ->
            user?.let {
                if (text.toString().lowercase() != it.lName.toString().lowercase()) {
                    Log.d(TAG, "data class: ${it.lName}")
                    Log.d(TAG, "edit text: $text")
                    binding.saveButtonProfile.isEnabled = true
                } else {
                    binding.saveButtonProfile.isEnabled = false
                }
            }
        }

        binding.bioTextViewEditText.doOnTextChanged { text, start, before, count ->
            user?.let {
                if (text.toString().lowercase() != it.bio.toString().lowercase()) {
                    Log.d(TAG, "data class: ${it.bio}")
                    Log.d(TAG, "edit text: $text")
                    binding.saveButtonProfile.isEnabled = true
                } else {
                    binding.saveButtonProfile.isEnabled = false
                }
            }
        }

        showPicture(image)
        viewModel.getProfile()




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICKER && resultCode == Activity.RESULT_OK) {
            imagePath = Matisse.obtainResult(data)[0]
            showPicture(imagePath.toString())
//            Glide.with(requireContext()).load(image).into(binding.profilePhoto)
        }
    }

    fun ImagePicker() {
        Matisse.from(this)
            .choose(MimeType.ofImage(), false)
            .capture(false)
            .captureStrategy(CaptureStrategy(true, "com.example.engineersguide"))
            .forResult(IMAGE_PICKER)
    }

    fun showPicture(imagePath: String) {
        Glide.with(this)
            .load(imagePath)
            .centerCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.profilepic)
            .circleCrop()
            .into(binding.profilePhoto)
    }

    fun saveEdite() {
        user?.apply {

            fName = binding.firstNameProfileEditText.text.toString()
            lName = binding.lastNameProfileEditText.text.toString()
            bio = binding.bioTextViewEditText.text.toString()
            email = FirebaseAuth.getInstance().currentUser?.email
//            password = FirebaseAuth.getInstance().
//            viewModel.UploadPhoto(imagePath)
            viewModel.save(user!!)

        }
    }

    @SuppressLint("SetTextI18n")
    fun observer() {
//        binding.saveButtonProfile.visibility = View.INVISIBLE
        viewModel.getUserLiveDate.observe(viewLifecycleOwner, {
            binding.firstNameProfileEditText.setText(it.fName)
            binding.lastNameProfileEditText.setText(it.lName)
            binding.bioTextViewEditText.setText(it.bio)
            binding.emailProfileTextView.text = "email: "+it.email
            user = it




            Log.d(TAG, it.toString())
        })
//        viewModel.UploadPhotosersLiveDate.observe(viewLifecycleOwner, {
//            showPicture("")
//        })
//        viewModel.saveusersLiveDate.observe(viewLifecycleOwner, {
//
//        })
//        viewModel.deleUserLiveDate.observe(viewLifecycleOwner, {
//
//        })
    }


}
