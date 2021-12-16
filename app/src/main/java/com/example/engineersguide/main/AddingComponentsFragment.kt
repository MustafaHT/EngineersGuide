package com.example.engineersguide.main

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentAddingComponentsBinding
import com.example.engineersguide.model.components.ComponentApi
import com.example.engineersguide.repositories.ApiServiceRepository
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


private lateinit var apiServiceRepository: ApiServiceRepository
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
//            val intentImage = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
////            intentImage.type = "image/*"
//                activity?.startActivityForResult(intentImage, 2)
            Toast.makeText(context, "This button is disabled", Toast.LENGTH_LONG).show()

        }

        binding.addComponent.setOnClickListener(){
//             submitComponen(
//                binding.titleEditText.text.toString(),
//                binding.descreptionEditText.text.toString(),
//                binding.functionlityEditText.text.toString(),
//                binding.equationsEditText.text.toString(),
//                binding.source1EditText.text.toString(),
//                binding.source2EditText.text.toString(),
//                binding.source3EditText.text.toString()
//            )
            findNavController().navigate(R.id.action_addingComponentsFragment_to_componentsFragment)
        }

        Log.d(TAG,mStorage.toString())



        apiServiceRepository = ApiServiceRepository(requireContext())

    }

//    private suspend fun submitComponent(title: String, description: String, functionality: String, equations:String, source1: String, source2: String, source3: String){
//        val call: Call<ComponentApi> = apiServiceRepository.retrofitApi.addComponents(title,description,functionality,equations,source1,source2,source3)
//        call.enqueue(object : Callback<ComponentApi>{
//            override fun onResponse(call: Call<ComponentApi>, response: Response<ComponentApi>) {
//                Snackbar.make(requireActivity(),requireView(),"${response.body()}",Snackbar.LENGTH_LONG).show()
//            }
//
//            override fun onFailure(call: Call<ComponentApi>, t: Throwable) {
//                Log.d("TAG","onFailure:${t.message}")
//            }
//
//        })
//    }


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