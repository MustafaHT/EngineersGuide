package com.example.engineersguide.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.engineersguide.R
import com.example.engineersguide.adapters.ComponentsRecyclerViewAdapter
import com.example.engineersguide.databinding.FragmentComponentsBinding
import com.example.engineersguide.helper.MyButton
import com.example.engineersguide.helper.MySwiperHelper
import com.example.engineersguide.listener.MyButtonClickListener
import com.example.engineersguide.model.components.ComponentApi
import com.example.engineersguide.repositories.SHARED_PREF_FILE
import com.google.firebase.auth.FirebaseAuth


private const val TAG = "ComponentsFragment"
class ComponentsFragment : Fragment() {


    private lateinit var binding: FragmentComponentsBinding

    private var allComponents = mutableListOf<ComponentApi>()

    private lateinit var componentsAdapter: ComponentsRecyclerViewAdapter
    private val componentsViewModel: ComponentsViewModel by activityViewModels()

    private lateinit var logoutItem: MenuItem
    private lateinit var profileItem: MenuItem

    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor

    private lateinit var selectedItem: ComponentApi


//    private lateinit var componentFragmentSwipeLayout:SwipeLayout

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

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        componentsViewModel.callComponents()

        componentsAdapter = ComponentsRecyclerViewAdapter(componentsViewModel)
        binding.componentsRecyclerView.adapter = componentsAdapter


        val swipe = object : MySwiperHelper(context, binding.componentsRecyclerView, 300) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                // add button
                buffer.add(
                    MyButton(requireActivity(),
                        "Delete",
                        30,
                        R.drawable.ic_baseline_delete_24,
                        Color.parseColor("#FF3c30"),
                        object : MyButtonClickListener {
                            override fun onClick(pos: Int) {
                                Log.d(TAG,"not working")
                                viewHolder.layoutPosition
                                componentsViewModel.deleteComponent(allComponents[viewHolder.layoutPosition].id.toInt())
                                componentsViewModel.callComponents()
                            }
                        }

                    )
                )

                buffer.add(
                    MyButton(requireActivity(),
                        "Edit",
                        30,
                        R.drawable.ic_baseline_edit_24,
                        Color.parseColor("#FF9502"),
                        object : MyButtonClickListener {
                            override fun onClick(pos: Int) {
                                Toast.makeText(
                                    context,
                                    "edit button has been clicked",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                )

            }

        }



        observers()



        binding.addingComponentsButton.setOnClickListener() {
            findNavController().navigate(R.id.action_componentsFragment_to_addingComponentsFragment)

        }
//        componentFragmentSwipeLayout = view.findViewById(R.layout.components_item_layout)
//        componentFragmentSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut)
//        componentFragmentSwipeLayout.addDrag(SwipeLayout.DragEdge.Left,componentFragmentSwipeLayout.findViewById(R.id.linear_sol))
//        componentFragmentSwipeLayout.addDrag(SwipeLayout.DragEdge.Right,componentFragmentSwipeLayout.findViewById(R.id.linear_sag))

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

        when (item.itemId) {
            R.id.logout_item -> {
                FirebaseAuth.getInstance().signOut()
                sharedPref =
                    requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                sharedPrefEditor = sharedPref.edit()
                sharedPrefEditor.putBoolean("a", false)
                sharedPrefEditor.commit()
                findNavController().navigate(R.id.action_componentsFragment_to_loginFragment)

                if (logoutItem.isChecked) {
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
                    if (newText.isNotBlank()) {
                        componentsAdapter.submitList(allComponents.filter {
                            it.componentTitle.lowercase().contains(newText!!.lowercase())
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

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.additional_menu, menu)

    }

//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.edit_item -> Toast.makeText(requireContext(), "edit", Toast.LENGTH_SHORT).show()
//            R.id.delete_item -> Toast.makeText(requireContext(), "delete", Toast.LENGTH_SHORT)
//                .show()
//        }
//        return super.onContextItemSelected(item)
//
//    }

}
