package com.example.engineersguide.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
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

        componentsAdapter = ComponentsRecyclerViewAdapter(componentsViewModel)
        binding.componentsRecyclerView.adapter = componentsAdapter

//        setItemTouchHelper()

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
                                componentsViewModel.deleteComponent(selectedItem)
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

        componentsViewModel.callComponents()

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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_item -> Toast.makeText(requireContext(), "edit", Toast.LENGTH_SHORT).show()
            R.id.delete_item -> Toast.makeText(requireContext(), "delete", Toast.LENGTH_SHORT)
                .show()
        }
        return super.onContextItemSelected(item)

    }

//    private fun setItemTouchHelper(){
//        ItemTouchHelper(object : ItemTouchHelper.Callback(){
//
//            private val limitScrollX = dipToPx(300f,this@ComponentsFragment)
//            private var currentScrollX = 0
//            private var currentScrollXWhenInActivity = 0
//            private var initXWhenInActivity = 0f
//            private var firstInActive = false
//
//            override fun getMovementFlags(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder
//            ): Int {
//                val dragFlags = 0
//                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//                return makeMovementFlags(dragFlags,swipeFlags)
//            }
//
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return true
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
//
//            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
//                return Integer.MAX_VALUE.toFloat()
//            }
//
//            override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
//                return  Integer.MAX_VALUE.toFloat()
//            }
//
//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
//
//                    if (dX == 0f){
//                        currentScrollX = viewHolder.itemView.scrollX
//                        firstInActive = true
//                    }
//
//                    if (isCurrentlyActive){
//                        var scrollOffset = currentScrollX + (-dX).toInt()
//                        if (scrollOffset > limitScrollX){
//                            scrollOffset = limitScrollX
//                        }else if(scrollOffset < 0){
//                            scrollOffset = 0
//                    }
//                        viewHolder.itemView.scrollTo(scrollOffset, 0)
//                    }else{
//                        if(firstInActive){
//                            firstInActive = false
//                            currentScrollXWhenInActivity = viewHolder.itemView.scrollX
//                            initXWhenInActivity = dX
//                        }
//                        if (viewHolder.itemView.scrollX < limitScrollX){
//                            viewHolder.itemView.scrollTo((currentScrollXWhenInActivity * dX / initXWhenInActivity).toInt(),0)
//                        }
//                    }
//                }
//            }
//
//            override fun clearView(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder
//            ) {
//                super.clearView(recyclerView, viewHolder)
//
//                if (viewHolder.itemView.scrollX > limitScrollX){
//                    viewHolder.itemView.scrollTo(limitScrollX, 0)
//                }else if(viewHolder.itemView.scrollX < 0){
//                    viewHolder.itemView.scrollTo(0,0)
//                }
//            }
//
//        }).apply {
//            attachToRecyclerView(binding.componentsRecyclerView)
//        }
//    }
//
//
//    private fun dipToPx(dipValue:Float, context: ComponentsFragment):Int{
//        return (dipValue * context.resources.displayMetrics.density).toInt()
//    }

}
