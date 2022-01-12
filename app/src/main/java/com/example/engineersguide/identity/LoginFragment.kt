package com.example.engineersguide.identity

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy


class LoginFragment : Fragment() {
    var SHARED_PREF_FILE = "Auth"
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor

    private lateinit var mLoginViewModel: LoginViewModel


    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireActivity().getSharedPreferences(
            SHARED_PREF_FILE,
            Context.MODE_PRIVATE
        )

        val status = sharedPref.getBoolean("auth",false)

        if (status){
            findNavController().navigate(R.id.action_loginFragment_to_componentsFragment)
        }

        binding.button.setOnClickListener {
            showImagePicker()
        }



        binding.registerButton.setOnClickListener() {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginButton.setOnClickListener() {
            val username: String = binding.emailLoginEditText.text.toString()
            val password: String = binding.passwordLoginEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            sharedPrefEditor = sharedPref.edit()
                            sharedPrefEditor.putBoolean("auth", true)
                            sharedPrefEditor.commit()
                            Toast.makeText(
                                context,
                                "Logged in Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

//                            binding.loginMotionLayout.setTransitionVisibility(MotionLayout.VISIBLE)
                            findNavController().navigate(R.id.action_loginFragment_to_componentsFragment)
                        } else {
                            Toast.makeText(
                                context,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    context,
                    "Please Enter Your Username And Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun showImagePicker(){
        val IMAGE_PICKER = 0
        Matisse.from(this)
            .choose(MimeType.ofImage(),false)
            .capture(true)
            .captureStrategy(CaptureStrategy(true,"com.example.engineersguide"))
            .forResult(IMAGE_PICKER)
    }
}