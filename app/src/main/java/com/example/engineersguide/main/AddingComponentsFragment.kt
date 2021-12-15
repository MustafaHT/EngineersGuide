package com.example.engineersguide.main

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentAddingComponentsBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

private const val TAG = "AddingComponentsFragmen"
class AddingComponentsFragment : Fragment() {

    private lateinit var binding: FragmentAddingComponentsBinding

    var mStorage:StorageReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddingComponentsBinding.inflate(inflater,container,false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mStorage = FirebaseStorage.getInstance().reference

        binding.componentImageButton.setOnClickListener(){
            val intentImage = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            intentImage.type = "image/*"
                activity?.startActivityForResult(intentImage, 2)

        }

        Log.d(TAG,mStorage.toString())


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG,"BEFORE CONDITION")
        if (requestCode == 2 && resultCode == RESULT_OK){
            val uriImage = data?.data
            Log.d(TAG,"IMAGE")

             val filePath = mStorage?.child(Calendar.getInstance().time.toString())
            Log.d(TAG,"file path: $filePath")
            if (uriImage != null) {
                Log.d(TAG," URI Image: $uriImage")
                filePath?.putFile(uriImage)?.addOnSuccessListener {
                    Log.d(TAG,"Successfully added")

                    Toast.makeText(context, "Upload Image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}