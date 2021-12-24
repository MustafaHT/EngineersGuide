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
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentOnAttachListener
import com.example.engineersguide.databinding.FragmentWebBinding
import kotlin.Unit.toString

private const val TAG = "WebFragment"
class WebFragment : Fragment() {

    private lateinit var binding:FragmentWebBinding
    private lateinit var wb_webView:WebView
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

        webViewSetup()

    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetup(){
        val wb_webViewClient = WebViewClient()

        wb_webView.apply {
            loadUrl("www.google.com/")
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
            Log.d(TAG,"why why!!!")
        }
    }
}