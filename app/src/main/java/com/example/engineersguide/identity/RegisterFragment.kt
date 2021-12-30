package com.example.engineersguide.identity

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.engineersguide.R
import com.example.engineersguide.databinding.FragmentRegisterBinding
import com.example.engineersguide.datamodels.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private const val TAG = "RegisterFragment"

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    private val userCollectionRef = Firebase.firestore.collection("Users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        //========================================================================
//        val uid = auth.currentUser?.uid
//        var databaseRef = FirebaseDatabase.getInstance().getReference("User")
        //========================================================================

        binding.backRegisterImageButton.setOnClickListener() {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerButtonRegister.setOnClickListener() {
            val fName: String = binding.firstNameRegisterEditText.text.toString()
            val lName: String = binding.lastNameRegisterEditText.text.toString()
            val email: String = binding.emailRegisterEditText.text.toString()
            val password: String = binding.passwordRegisterEditText.text.toString()
            val confirmPassword: String = binding.confirmPasswordRegisterEditText.text.toString()
            val bioTextView: String = binding.bioTextViewEditText.text.toString()

            val user = User(fName, lName, email, password, bioTextView)
            saveUser(user)
            Log.d(TAG, "")


//  ===========================================================================
//            if(uid != null){
//                databaseRef.child(uid).setValue(user)
//            }
//  ===========================================================================
            if (fName.isNotEmpty() && lName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        if (password == confirmPassword) {
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Registered Successfully",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                            } else {
                                Toast.makeText(
                                    context,
                                    task.exception?.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Your Password Does Not Match",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
            } else {
                Toast.makeText(context, "Please Fill Up All The Blanks", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUser(person: User) = CoroutineScope(Dispatchers.IO).launch {

        try {
            userCollectionRef.add(person).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

    }
}
