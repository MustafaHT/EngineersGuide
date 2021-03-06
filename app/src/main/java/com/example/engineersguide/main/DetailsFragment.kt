package com.example.engineersguide.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentDetailsBinding
import com.example.engineersguide.model.components.ComponentModel
import com.example.engineersguide.repositories.FirebaseRepository
import com.example.engineersguide.util.BottomNavHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var selectedComponent: ComponentModel
    private val viewModel: ComponentsViewModel by activityViewModels()


    private lateinit var deleteItem: MenuItem

    private var image: Uri? = null

    private val IMAGE_PICKER = 0

    private val firestorageViewModel = FirestorageViewModel()

    private val firebaseRepo = FirebaseRepository


    private val STORAGE_CODE = 1001


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BottomNavHelper.get().performHideBar()
        BottomNavHelper.get().hideFab()

        viewModel.callComponents()

        viewModel.selectedComponent.observe(viewLifecycleOwner, Observer {
            it?.let { component ->
                binding.titleTextViewDetails.text = component.componentName
                Glide.with(requireContext()).load(component.componentImageUrl).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.component).into(binding.imageViewDetails)
                binding.descreptionTextViewDetails.text = component.description
                binding.functionalityTextViewDetails.text = component.functionality
                binding.equationsTextViewDetails.text = component.equations
                binding.source1TextViewDetails.text = component.source1
                binding.source2TextViewDetails.text = component.source2
                binding.source3TextViewDetails.text = component.source3
                selectedComponent = component

                binding.createdBy.text = "created by: ${selectedComponent.username}"

                if(selectedComponent.componentImageUrl != ""){
                    binding.imageViewDetails.setOnClickListener {
                        val view = View.inflate(context, R.layout.image, null)
                        val builder = AlertDialog.Builder(context)
                        builder.setView(view)

                        val dialogImage = view.findViewById<ImageView>(R.id.imageDialog)



                        Glide.with(requireContext())
                            .load(selectedComponent.componentImageUrl)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(dialogImage)


                        val dialog = builder.create()
                        dialog.show()
                        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    }
                }else{
                    binding.imageViewDetails.setOnClickListener {
                        Toast.makeText(
                            requireContext(),
                            "there is no picture for this component",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }



                //==========================================================================================================================

                if(selectedComponent.source1.isEmpty() && selectedComponent.source2.isEmpty() && selectedComponent.source3.isEmpty()) {
                    binding.resourcesTextView.visibility = View.INVISIBLE
                    binding.imageButtonForSource1.visibility = View.INVISIBLE
                    binding.imageButtonForSource2.visibility = View.INVISIBLE
                    binding.imageButtonForSource3.visibility = View.INVISIBLE

                }

                    if (selectedComponent.source1.isNotEmpty() && selectedComponent.source2.isEmpty() && selectedComponent.source3.isEmpty()) {
                        binding.imageButtonForSource2.visibility = View.INVISIBLE
                        binding.imageButtonForSource3.visibility = View.INVISIBLE

                    }

                        if(selectedComponent.source1.isNotEmpty() && selectedComponent.source2.isNotEmpty() && selectedComponent.source3.isEmpty()){
                            binding.imageButtonForSource3.visibility = View.INVISIBLE
                        }


//=====================================================================


                if (binding.descreptionTextViewDetails.text.length >= 1000) {
                    binding.viewDescreptionDetails.visibility = View.VISIBLE
                    binding.descreptionTextViewDetails.text =
                        binding.descreptionTextViewDetails.text.substring(0, 900 - 3) + "..."
                    binding.viewDescreptionDetails.setOnClickListener {
                        if (binding.viewDescreptionDetails.text == "view more") {
                            binding.descreptionTextViewDetails.text = component.description
                            binding.viewDescreptionDetails.text = "view less"
                        } else {
                            binding.descreptionTextViewDetails.text =
                                binding.descreptionTextViewDetails.text.substring(
                                    0,
                                    900 - 3
                                ) + "..."
                            binding.viewDescreptionDetails.text = "view more"
                        }
                    }
                }

                if (binding.functionalityTextViewDetails.text.length >= 400) {
                    binding.viewFunctionalityDetails.visibility = View.VISIBLE
                    binding.functionalityTextViewDetails.text =
                        binding.functionalityTextViewDetails.text.substring(0, 300 - 3) + "..."
                    binding.viewFunctionalityDetails.setOnClickListener {
                        if (binding.viewFunctionalityDetails.text == "view more") {
                            binding.functionalityTextViewDetails.text = component.description
                            binding.viewFunctionalityDetails.text = "view less"
                        } else {
                            binding.functionalityTextViewDetails.text =
                                binding.functionalityTextViewDetails.text.substring(
                                    0,
                                    300 - 3
                                ) + "..."
                            binding.viewFunctionalityDetails.text = "view more"
                        }
                    }
                }

            }
        })

        binding.backButtonDetailsFragment.setOnClickListener {
            findNavController().popBackStack()
        }



        binding.imageButtonForSource1.setOnClickListener {
            Bundle().apply {
                putString("link", selectedComponent.source1)
                if (selectedComponent.source1.isEmpty()){
                    Toast.makeText(requireContext(), "you don't have any source to go to", Toast.LENGTH_SHORT).show()
                }else {
                    it.findNavController().navigate(R.id.action_detailsFragment_to_webFragment, this)
                }
            }

        }
        binding.imageButtonForSource2.setOnClickListener {
            Bundle().apply {
                putString("link", selectedComponent.source2)
                if (selectedComponent.source2.isEmpty()){
                    Toast.makeText(requireContext(), "you don't have any source to go to", Toast.LENGTH_SHORT).show()
                }else {
                    it.findNavController().navigate(R.id.action_detailsFragment_to_webFragment, this)
                }
            }
        }
        binding.imageButtonForSource3.setOnClickListener {
            Bundle().apply {
                putString("link", selectedComponent.source3)
                if (selectedComponent.source3.isEmpty()){
                    Toast.makeText(requireContext(), "you don't have any source to go to", Toast.LENGTH_SHORT).show()
                }else {
                    it.findNavController().navigate(R.id.action_detailsFragment_to_webFragment, this)
                }
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.additional_menu, menu)
        deleteItem = menu.findItem(R.id.delete_item)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete_item -> {
                viewModel.deleteComponent(selectedComponent.id.toInt())
//                findNavController().navigate(R.id.action_detailsFragment_to_componentsFragment)
            }
            R.id.edit_item ->{
                findNavController().navigate(R.id.action_detailsFragment_to_editFragment)
            }
            R.id.pdf_item -> {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                    if (checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        requestPermissions(permission, STORAGE_CODE)
                    }else{
                        savePDF()
                    }
                }else{
                    savePDF()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun savePDF() {
        // this document
        val mDoc = com.itextpdf.text.Document()
        //this is for the file name
        val mFileName = SimpleDateFormat("yyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        //this is the path of the Pdf File
//        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
//        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val mFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + mFileName + ".pdf"
        try {

            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            mDoc.open()

            val data = binding.titleTextViewDetails.text.toString().trim()
            val picData = Glide.with(requireContext()).load(selectedComponent.componentImageUrl).into(binding.imageViewDetails)
            val data2 = binding.textView13.text.toString().trim()
            val data3 = selectedComponent.description.trim()
            val data4 = binding.textView17.text.toString().trim()
            val data5 = selectedComponent.functionality.trim()
            val data6 = binding.textView20.text.toString().trim()
            val data7 = selectedComponent.equations.trim()
            val data8 = binding.source1TextViewDetails.text.toString().trim()
            val data9 = binding.source2TextViewDetails.text.toString().trim()
            val data0 = binding.source3TextViewDetails.text.toString().trim()
            mDoc.addAuthor("Mustafa")
            mDoc.add(Paragraph(data))
//            mDoc.add(Para)
            mDoc.add(Paragraph(data2))
            mDoc.add(Paragraph(data3))
            mDoc.add(Paragraph(data4))
            mDoc.add(Paragraph(data5))
            mDoc.add(Paragraph(data6))
            mDoc.add(Paragraph(data7))
            mDoc.add(Paragraph(data8))
            mDoc.add(Paragraph(data9))
            mDoc.add(Paragraph(data0))
            mDoc.close()
            Toast.makeText(context, "$mFileName.pdf\n is create to \n$mFilePath", Toast.LENGTH_SHORT).show()




        }catch (e:Exception){
            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            STORAGE_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePDF()
                }else{
                    Toast.makeText(context, "Permission denied!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}