package com.example.engineersguide.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.engineersguide.R
import com.example.engineersguide.adapters.ComponentsRecyclerViewAdapter
import com.example.engineersguide.databinding.FragmentComponentsBinding
import com.example.engineersguide.model.components.Components


class ComponentsFragment : Fragment() {

    private lateinit var binding:FragmentComponentsBinding

    private var allComponents = listOf<Components>()

    private lateinit var componentsAdapter : ComponentsRecyclerViewAdapter
    private val componentsViewModel: ComponentsViewModel by activityViewModels()

//    private lateinit var logoutItem: MenuItem
//    private lateinit var profileItem: MenuItem



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentComponentsBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentsAdapter = ComponentsRecyclerViewAdapter(componentsViewModel)
        binding.componentsRecyclerView.adapter = componentsAdapter


        observers()

//        componentsViewModel.callComponents()

    }

    fun observers(){

        componentsViewModel.componentsLiveData.observe(viewLifecycleOwner, {

            binding.progressBar.animate().alpha(0f).setDuration(1000)
            componentsAdapter.submitList(it)
            allComponents = it
            binding.componentsRecyclerView.animate().alpha(1f)

        })

//        componentsViewModel.componentsErrorLiveData.observe(viewLifecycleOwner, { error ->
//            error?.let {
//                Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
//
//                if (error == "Unauthorized")
//                    findNavController().navigate()
//
//                componentsViewModel.componentsErrorLiveData.postValue(null)
//
//            }
//        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//
//        }
//        R.id.profile_item ->{
//            findNavController().navigate(R.id.)
//        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//    }
}