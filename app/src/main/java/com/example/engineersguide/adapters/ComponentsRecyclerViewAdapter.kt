package com.example.engineersguide.adapters

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.engineersguide.R
import com.example.engineersguide.main.ComponentsViewModel
import com.example.engineersguide.model.components.ComponentModel
import java.lang.ref.WeakReference


class ComponentsRecyclerViewAdapter(private val context: Context, val viewModel: ComponentsViewModel) :
    RecyclerView.Adapter<ComponentsRecyclerViewAdapter.ComponentsViewHolder>() {


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ComponentModel>() {
        override fun areItemsTheSame(oldItem: ComponentModel, newItem: ComponentModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ComponentModel, newItem: ComponentModel): Boolean {
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
        holder.titleTextview.text = item.componentName
        holder.descreptionTextView.text = item.description

        Glide.with(context)
            .load(item.componentImageUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.componentImageView)


        holder.itemView.setOnClickListener {
            viewModel.selectedComponent.postValue(item)
            it.findNavController().navigate(R.id.action_componentsFragment_to_detailsFragment)
        }

        if (holder.descreptionTextView.text.length >= 75) {
            holder.descreptionTextView.text =
                holder.descreptionTextView.text.substring(0, 75 - 3) + "..."
        }


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
        val componentImageView: ImageView = itemView.findViewById(R.id.desscreption_imageView_Component)

    }

    fun submitList(list: List<ComponentModel>) {
        differ.submitList(list)
    }
    fun getList():List<ComponentModel> {
        return differ.currentList
    }


}

