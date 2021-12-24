package com.example.engineersguide.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.engineersguide.R
import com.example.engineersguide.main.ComponentsViewModel
import com.example.engineersguide.model.components.ComponentApi



class ComponentsRecyclerViewAdapter(val viewModel: ComponentsViewModel) :
    RecyclerView.Adapter<ComponentsRecyclerViewAdapter.ComponentsViewHolder>() {

    val DIFF_CALLBACK = object :DiffUtil.ItemCallback<ComponentApi>(){
        override fun areItemsTheSame(oldItem: ComponentApi, newItem: ComponentApi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ComponentApi, newItem: ComponentApi): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComponentsRecyclerViewAdapter.ComponentsViewHolder {

        return ComponentsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.components_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ComponentsViewHolder, position: Int) {

        val item = differ.currentList[position]
        holder.titleTextview.text = item.componentTitle
        holder.descreptionTextView.text = item.description


        holder.itemView.setOnClickListener {
            viewModel.selectedComponent.postValue(item)
            it.findNavController().navigate(R.id.action_componentsFragment_to_detailsFragment)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    class ComponentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextview:TextView = itemView.findViewById(R.id.titleTextView)
        val descreptionTextView:TextView = itemView.findViewById(R.id.DescreptionTextView)
        val cardView:CardView = itemView.findViewById(R.id.CardView)
    }

    fun submitList(list: List<ComponentApi>) {
        differ.submitList(list)
    }



}

