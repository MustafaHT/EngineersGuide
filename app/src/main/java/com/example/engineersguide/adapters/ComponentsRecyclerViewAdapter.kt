package com.example.engineersguide.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        holder.titleTextview.text = item.componentName
        holder.descreptionTextView.text = item.description
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    class ComponentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextview:TextView = itemView.findViewById(R.id.titleTextView)
        val descreptionTextView:TextView = itemView.findViewById(R.id.DescreptionTextView)
    }

    fun submitList(list: List<ComponentApi>) {
        differ.submitList(list)
    }

}

