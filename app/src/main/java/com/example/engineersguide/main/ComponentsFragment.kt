package com.example.engineersguide.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.engineersguide.R
import com.example.engineersguide.adapters.ComponentsRecyclerViewAdapter
import com.example.engineersguide.databinding.FragmentComponentsBinding
import com.example.engineersguide.model.components.ComponentApi
import com.example.engineersguide.repositories.SHARED_PREF_FILE
import com.google.firebase.auth.FirebaseAuth


class ComponentsFragment : Fragment() {


    private lateinit var binding: FragmentComponentsBinding

    private var allComponents = mutableListOf<ComponentApi>()

    private lateinit var componentsAdapter: ComponentsRecyclerViewAdapter
    private val componentsViewModel: ComponentsViewModel by activityViewModels()

    private lateinit var logoutItem: MenuItem
    private lateinit var profileItem: MenuItem

    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor

//    private lateinit var user: FirebaseAuth


//    private lateinit var logoutItem: MenuItem
//    private lateinit var profileItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

//        val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
//        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
//
//        if (firebaseUser != null ){
//            findNavController().navigate(R.id.componentsFragment)
//        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentComponentsBinding.inflate(inflater, container, false)
        return (binding.root)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        componentsAdapter = ComponentsRecyclerViewAdapter(componentsViewModel)
        binding.componentsRecyclerView.adapter = componentsAdapter


        observers()

       componentsViewModel.callComponents()

        binding.addingComponentsButton.setOnClickListener(){
            findNavController().navigate(R.id.action_componentsFragment_to_addingComponentsFragment)


        }
    }

    fun observers() {

        componentsViewModel.componentsLiveData.observe(viewLifecycleOwner, {

            binding.progressBar.animate().alpha(0f).setDuration(1000)
            componentsAdapter.submitList(it)
            allComponents.addAll(it)
            binding.componentsRecyclerView.animate().alpha(1f)

        })

        componentsViewModel.componentsErrorLiveData.observe(viewLifecycleOwner, { error ->
            error?.let {
                Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()

                if (error == "Unauthorized")
//                    findNavController().navigate(R.id.)

                componentsViewModel.componentsErrorLiveData.postValue(null)

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.logout_item -> {
                FirebaseAuth.getInstance().signOut()
                sharedPref =
                    requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                sharedPrefEditor = sharedPref.edit()
                sharedPrefEditor.putBoolean("a", false)
                sharedPrefEditor.commit()
                findNavController().navigate(R.id.action_componentsFragment_to_loginFragment)

                if (logoutItem.isChecked){
                    Toast.makeText(context, "LoggedOut Successfully", Toast.LENGTH_SHORT).show()
                }

            }


        }

//        R.id.profile_item -> {
//            findNavController().navigate(R.id.)

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.app_bar_menu, menu)

        logoutItem = menu.findItem(R.id.logout_item)
        val search = menu.findItem(R.id.app_bar_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Search Component"





        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isNotBlank()){
                        componentsAdapter.submitList(allComponents.filter {
                            it.componentName.lowercase().contains(newText!!.lowercase())
                        })
                    }
                }
                return true
            }

        })


        return super.onCreateOptionsMenu(menu, inflater)
    }


//        val token = sharedPref.getString(TOKEN_KEY, "")
//
//        if (token!!.isEmpty()) {
//            logoutItem.isVisible = false
//            profileItem.isVisible = false
//        }

}
