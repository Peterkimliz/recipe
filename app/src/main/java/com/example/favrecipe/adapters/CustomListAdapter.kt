package com.example.favrecipe.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favrecipe.databinding.CustomListLayoutBinding
import com.example.favrecipe.views.activities.AddUpdateDishActivity

class CustomListAdapter(
    val activity: Activity,
    val listItems: List<String>,
    val selection: String) : RecyclerView.Adapter<CustomListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CustomListLayoutBinding = CustomListLayoutBinding.inflate(LayoutInflater.from(activity), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        holder.tvText.text = item

        holder.itemView.setOnClickListener {
            if (activity is AddUpdateDishActivity) {
                activity.selectedListItem(item, selection)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    class ViewHolder(view: CustomListLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val tvText = view.tvText
    }
}