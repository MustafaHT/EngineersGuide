package com.example.engineersguide.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.engineersguide.R
import com.example.engineersguide.main.ComponentsViewModel
import com.example.engineersguide.model.components.Components

class ComponentsRecyclerViewAdapter(context:Context,private val list: List<Components>) :
    RecyclerView.Adapter<ComponentsRecyclerViewAdapter.ComponentsViewHolder>() {

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

        val item = list[position]
        holder.titleTextview.text = item.componentName
        holder.descreptionTextView.text = item.description
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ComponentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextview:TextView = itemView.findViewById(R.id.titleTextView)
        val descreptionTextView:TextView = itemView.findViewById(R.id.DescreptionTextView)
    }

}