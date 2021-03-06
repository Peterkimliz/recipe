package com.example.favrecipe.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favrecipe.databinding.AllDishesLayoutBinding
import com.example.favrecipe.model.FavDish
import com.example.favrecipe.views.fragments.AllDishFragment

class FavDishAdapter(private val fragment:Fragment):RecyclerView.Adapter<FavDishAdapter.ViewHolder>(){
    private var dishes:List<FavDish> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun dishes(listOfDishes:List<FavDish>){
        dishes=listOfDishes
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:AllDishesLayoutBinding= AllDishesLayoutBinding.inflate(LayoutInflater.from(fragment.context),parent,false)
        return ViewHolder(binding)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val dish=dishes[position]
        Glide.with(fragment)
            .load(dish.image)
            .centerInside()
            .into(holder.ivDishImage)
        holder.tvTitle.text=dish.title
        holder.itemView.setOnClickListener{
            if (fragment is AllDishFragment){
                fragment.moveToDetailsFragment()
            }
        }
    }

    override fun getItemCount(): Int {
       return dishes.size
    }
    inner class ViewHolder(view:AllDishesLayoutBinding):RecyclerView.ViewHolder(view.root){
        val ivDishImage=view.ivDishImage
        val tvTitle=view.tvDishTitle
    }
}