package com.example.engineersguide.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.engineersguide.R
import com.example.engineersguide.main.ComponentsViewModel
import com.example.engineersguide.model.components.ComponentApi
import java.lang.ref.WeakReference


class ComponentsRecyclerViewAdapter(val viewModel: ComponentsViewModel) :
    RecyclerView.Adapter<ComponentsRecyclerViewAdapter.ComponentsViewHolder>() {

    private lateinit var selevetedComponent: ComponentApi

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ComponentApi>() {
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
    ): ComponentsViewHolder {

        return ComponentsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.components_item_layout,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ComponentsViewHolder, position: Int) {

        val item = differ.currentList[position]
        holder.titleTextview.text = item.componentTitle
        holder.descreptionTextView.text = item.description


        holder.itemView.setOnClickListener {
            viewModel.selectedComponent.postValue(item)
            it.findNavController().navigate(R.id.action_componentsFragment_to_detailsFragment)
        }

        if (holder.descreptionTextView.text.length >= 205) {
            holder.descreptionTextView.text =
                holder.descreptionTextView.text.substring(0, 205 - 3) + "..."
        }

//        holder.onDeleteClcik = {
////            viewModel.deleteComponent(selevetedComponent)
//        }



    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun setComponent(index:Int){
        viewModel.selectedComponent.postValue(differ.currentList[index])

    }


    class ComponentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val view = WeakReference(itemView)

        val titleTextview: TextView = itemView.findViewById(R.id.title_textView_Component)
        val descreptionTextView: TextView = itemView.findViewById(R.id.descreption_textView_Component)
    }

    fun submitList(list: List<ComponentApi>) {
        differ.submitList(list)
    }
    fun getList():List<ComponentApi> {
        return differ.currentList
    }


}

