package com.example.engineersguide.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.engineersguide.R
import com.example.engineersguide.adapters.ComponentsRecyclerViewAdapter
import com.example.engineersguide.api.ComponentsAPI
import com.example.engineersguide.databinding.FragmentAddingComponentsBinding
import com.example.engineersguide.databinding.FragmentDetailsBinding
import com.example.engineersguide.model.components.ComponentApi
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import org.w3c.dom.Document
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var selectedComponent: ComponentApi
    private val viewModel: ComponentsViewModel by activityViewModels()

    private val STORAGE_CODE = 1001


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedComponent.observe(viewLifecycleOwner, Observer {
            it?.let { component ->
                binding.titleTextViewDetails.text = component.componentTitle
                binding.descreptionTextViewDetails.text = component.description
                binding.functionalityTextViewDetails.text = component.functionality
                binding.equationsTextViewDetails.text = component.equations
                binding.source1TextViewDetails.text = component.source1
                binding.source2TextViewDetails.text = component.source2
                binding.source3TextViewDetails.text = component.source3
                selectedComponent = component


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



        binding.imageButtonForSource1.setOnClickListener {
            Bundle().apply {
                putString("link", selectedComponent.source1)
                it.findNavController().navigate(R.id.action_detailsFragment_to_webFragment, this)
            }

        }
        binding.imageButtonForSource2.setOnClickListener {
            Bundle().apply {
                putString("link", selectedComponent.source2)
                it.findNavController().navigate(R.id.action_detailsFragment_to_webFragment, this)
            }
        }
        binding.imageButtonForSource3.setOnClickListener {
            Bundle().apply {
                putString("link", selectedComponent.source3)
                it.findNavController().navigate(R.id.action_detailsFragment_to_webFragment, this)
            }
        }


        binding.pdfButton.setOnClickListener {
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
//            val picData =
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
//            mDoc.add(Paragraph(data))
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