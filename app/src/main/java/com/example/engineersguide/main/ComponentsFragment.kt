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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.engineersguide.R
import com.example.engineersguide.adapters.ComponentsRecyclerViewAdapter
import com.example.engineersguide.databinding.FragmentComponentsBinding
import com.example.engineersguide.helper.MyButton
import com.example.engineersguide.helper.MySwiperHelper
import com.example.engineersguide.listener.MyButtonClickListener
import com.example.engineersguide.model.components.ComponentModel
import com.example.engineersguide.repositories.SHARED_PREF_FILE
import com.google.firebase.auth.FirebaseAuth


private const val TAG = "ComponentsFragment"
class ComponentsFragment : Fragment() {


    private lateinit var binding: FragmentComponentsBinding

    private var allComponents = mutableListOf<ComponentModel>()

    private lateinit var componentsAdapter: ComponentsRecyclerViewAdapter
    private val componentsViewModel: ComponentsViewModel by activityViewModels()

    private lateinit var logoutItem: MenuItem
    private lateinit var profileItem: MenuItem

    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor

    private lateinit var selectedComponent: ComponentModel
    private val viewModel: ComponentsViewModel by activityViewModels()


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
        binding.bottomNavigationView.background = null
        componentsViewModel.callComponents()

        componentsAdapter = ComponentsRecyclerViewAdapter(requireContext(),componentsViewModel)
        binding.componentsRecyclerView.adapter = componentsAdapter

//        if(componentsAdapter.itemCount == 0){
//            binding.noComponentImageViewComponentFragment.visibility = View.VISIBLE
//        }else{
//            binding.noComponentImageViewComponentFragment.visibility = View.INVISIBLE
//        }

        refreshApp()

        binding.componentsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(recyclerView.canScrollVertically(1)){
                    binding.bottomBar.hideOnScroll = true


                }else if(!recyclerView.canScrollVertically(-1)){
                    Log.d(TAG,"I'm In")
                    binding.bottomBar.hideOnScroll = false
                    binding.fab.show()
                    binding.bottomBar.performShow()


                }
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy < 0){
                    binding.fab.show()
                }else if(dy > 0){
                    if (binding.bottomBar.hideOnScroll){
                        binding.fab.hide()
                    }else{
                        binding.fab.show()
                    }
                }
            }
        })


        val swipe = object : MySwiperHelper(context, binding.componentsRecyclerView, 300) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                // add button
                buffer.add(
                    MyButton(requireActivity(),
                        "Delete",
                        0,
                        (R.drawable.ic_baseline_delete_24),
                        Color.parseColor("#FF3c30"),
                        object : MyButtonClickListener {
                            override fun onClick(pos: Int) {
                                Log.d(TAG,"not working")
                                viewHolder.layoutPosition
                                Log.d(TAG,"$pos")
                                Log.d(TAG,"${componentsAdapter.getList()[pos]}")

                                componentsViewModel.deleteComponent(componentsAdapter.getList()[pos].id.toInt())

                            }
                        }

                    )
                )

                buffer.add(
                    MyButton(requireActivity(),
                        "Edit",
                        0,
                        (R.drawable.ic_baseline_edit_24),
                        Color.parseColor("#FF9502"),
                        object : MyButtonClickListener {
                            override fun onClick(pos: Int) {
                                viewHolder.layoutPosition
                                Log.d(TAG,pos.toString())
                                componentsAdapter.setComponent(pos)

                                view.findNavController().navigate(R.id.action_componentsFragment_to_editFragment)
                            }
                        }
                    )
                )

            }

        }



        observers()



        binding.fab.setOnClickListener() {
            findNavController().navigate(R.id.action_componentsFragment_to_addingComponentsFragment)

        }
//        componentFragmentSwipeLayout = view.findViewById(R.layout.components_item_layout)
//        componentFragmentSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut)
//        componentFragmentSwipeLayout.addDrag(SwipeLayout.DragEdge.Left,componentFragmentSwipeLayout.findViewById(R.id.linear_sol))
//        componentFragmentSwipeLayout.addDrag(SwipeLayout.DragEdge.Right,componentFragmentSwipeLayout.findViewById(R.id.linear_sag))

    }

    private fun refreshApp() {
        binding.swipeToRefresh.setOnRefreshListener {
            if (binding.swipeToRefresh.isRefreshing){
                viewModel.callComponents()
                binding.swipeToRefresh.isRefreshing = false
            }else{
                binding.swipeToRefresh.isRefreshing = true
            }
        }
    }

    fun observers() {


        componentsViewModel.componentsLiveData.observe(viewLifecycleOwner, {

            binding.progressBar.animate().alpha(0f).duration = 1000
            componentsAdapter.submitList(it)
            Log.d(TAG,"inside the componentsLiveDataObserve: $it")
            // here because of the addAll when ever i search about the component name the card will get repeat it self again and again
            // so to solve this problem i need to clear the components and get them after the clear method ...
            // here the components will be showed one time instead of making multiple times !!
            allComponents.clear()
            allComponents.addAll(it)
            binding.componentsRecyclerView.animate().alpha(1f)


            // here if the there are no components the gears Image will be shown inside components fragment
            if(it.isEmpty()){
                binding.noComponentImageViewComponentFragment.visibility = View.VISIBLE
            }else{
                binding.noComponentImageViewComponentFragment.visibility = View.INVISIBLE
            }

        })

        componentsViewModel.componentsErrorLiveData.observe(viewLifecycleOwner, { error ->
            error?.let {
                Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()

                if (error == "Unauthorized")
//                    findNavController().navigate(R.id.)

                    componentsViewModel.componentsErrorLiveData.postValue(null)

            }
        })

        componentsViewModel.deletedItemResponseLiveData.observe(viewLifecycleOwner, {
            it?.let {
                binding.progressBar.animate().alpha(1f).duration = 1000
                componentsViewModel.callComponents()

            }
        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.logout_item -> {
                FirebaseAuth.getInstance().signOut()
                sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                sharedPrefEditor = sharedPref.edit()
                sharedPrefEditor.putBoolean("auth", false)
                sharedPrefEditor.commit()
                findNavController().navigate(R.id.action_componentsFragment_to_loginFragment)

                if (logoutItem.isChecked) {
                    Toast.makeText(context, "LoggedOut Successfully", Toast.LENGTH_SHORT).show()
                }

            }
            R.id.setting_item -> {
                findNavController().navigate(R.id.action_componentsFragment_to_settingsFragment)
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



//
//     My Old code that made the problem
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if (newText != null) {
//                    if (newText.isNotBlank()) {
////                        allComponents.clear()
//                        componentsAdapter.submitList(allComponents.filter {
//                            it.componentTitle.lowercase().contains(newText!!.lowercase())
//                        })
//                    }
//                }
//                return true
//            }
//
//        })



        // ======================================================(NEW CODE FOR SEARCH BAR)==========================================


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                componentsAdapter.submitList(
                    allComponents.filter {
                        it.componentName.lowercase().contains(query!!.lowercase())
                                || it.description.lowercase().contains(query!!.lowercase())

                    }
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                componentsAdapter.submitList(
                    allComponents.filter {
                        it.componentName.lowercase().contains(newText!!.lowercase())
                                || it.description.lowercase().contains(newText!!.lowercase())

                    }
                )
                return true
            }

        })

        search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {

                return true
            }
            // here i used to return the previous list if the theres nothing inside the search bar ..s
            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                componentsAdapter.submitList(allComponents)
                return true
            }

        })









//        return super.onCreateOptionsMenu(menu, inflater)
    }
}
