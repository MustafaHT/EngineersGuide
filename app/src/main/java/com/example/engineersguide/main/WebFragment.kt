package com.example.engineersguide.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentOnAttachListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentWebBinding
import com.example.engineersguide.model.components.ComponentApi
import kotlin.Unit.toString

private const val TAG = "WebFragment"
class WebFragment : Fragment() {

    private lateinit var binding:FragmentWebBinding
    val selectedComponent = MutableLiveData<ComponentApi>()
    private val viewModel: ComponentsViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWebBinding.inflate(inflater,container,false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webViewSetup(requireArguments().getString("link",""))



    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetup(link:String){
        binding.wbWebView.webViewClient = WebViewClient()

        binding.wbWebView.apply {
                    if (link.substring(0,8) != "https://") {
                        loadUrl("https://www.google.com/search?q=${link}")
                        settings.javaScriptEnabled = true
                        settings.safeBrowsingEnabled = true
                    }else {
                        loadUrl(link)
                        settings.javaScriptEnabled = true
                        settings.safeBrowsingEnabled = true
                        }
                    }
        }
//    fun webViewOnBackPressed(){
//        if (binding.wbWebView.canGoBack()) binding.wbWebView.goBack()
//    }
//    companion object{
//        va
//    }
}