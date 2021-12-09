package com.example.engineersguide.identity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.engineersguide.MainActivity
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backRegisterImageButton.setOnClickListener(){
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerButtonRegister.setOnClickListener(){
            val fName:String = binding.firstNameRegisterEditText.text.toString()
            val lName:String = binding.lastNameRegisterEditText.text.toString()
            val email:String = binding.emailRegisterEditText.text.toString()
            val password:String = binding.passwordRegisterEditText.text.toString()
            val confirmPassword:String = binding.confirmPasswordRegisterEditText.text.toString()


            if(fName.isNotEmpty() && lName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(){
                    task ->

                    if (task.isSuccessful){
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }else{
                        Toast.makeText(context, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(context, "Please Fill Up All The Blanks", Toast.LENGTH_SHORT).show()
            }
        }
    }


}