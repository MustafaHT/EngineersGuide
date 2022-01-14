package com.example.engineersguide.identity

import android.app.NotificationChannel
import android.app.NotificationManager
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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.example.engineersguide.CHANNEL_ID
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentLoginBinding
import com.example.engineersguide.notificationId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy


class LoginFragment : Fragment() {
    var SHARED_PREF_FILE = "Auth"
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor

    private lateinit var mLoginViewModel: LoginViewModel


    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

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

//        val uid = auth.currentUser?.uid
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        sharedPref = requireActivity().getSharedPreferences(
            SHARED_PREF_FILE,
            Context.MODE_PRIVATE
        )

        val status = sharedPref.getBoolean("auth",false)

        if (status){
            findNavController().navigate(R.id.action_loginFragment_to_componentsFragment)
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

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descreptionText = "Notification Descreption"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                var descreption = descreptionText
            }

            var m_notificationMgr = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

          //  val notificationManger: NotificationManager = getSystemService(requireContext().NOTIFICATION_SERVICE) as NotificationManager
            m_notificationMgr.createNotificationChannel(channel)
        }
    }

    private fun Notification(){
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setContentTitle("Engineers Guide")
            .setContentText("Welcome To The Lighted Road")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())){
            notify(notificationId, builder.build())
        }
    }
}