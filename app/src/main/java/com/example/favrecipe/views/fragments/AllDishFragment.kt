package com.example.favrecipe.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.favrecipe.R
import com.example.favrecipe.adapters.FavDishAdapter
import com.example.favrecipe.application.FavDishApplication
import com.example.favrecipe.databinding.FragmentAllDishBinding
import com.example.favrecipe.utils.FavDishViewModelFactory
import com.example.favrecipe.viewmodels.FavDishViewModel
import com.example.favrecipe.views.activities.AddUpdateDishActivity
import com.example.favrecipe.views.activities.MainActivity

class AllDishFragment : Fragment() {
    private lateinit var binding: FragmentAllDishBinding

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAllDishes.layoutManager = GridLayoutManager(requireActivity(), 2);
        val allDishAdapter = FavDishAdapter(this@AllDishFragment)
        binding.rvAllDishes.adapter = allDishAdapter

        mFavDishViewModel.allDishesList.observe(viewLifecycleOwner) { data ->
            data.let {
                if (it.isNotEmpty()) {
                    binding.textHome.visibility = View.GONE
                    allDishAdapter.dishes(it)

                } else {
                    binding.textHome.visibility = View.VISIBLE
                    binding.rvAllDishes.visibility = View.GONE
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun moveToDetailsFragment(){
        findNavController().navigate(AllDishFragmentDirections.actionNavigationAllDishesToDishDetailsFragment())

        if (requireActivity() is MainActivity){
            (activity as MainActivity?)?.hideBottomNavigationBar()
        }
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationBar()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.optionAdd -> {
                startActivity(Intent(requireActivity(), AddUpdateDishActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}