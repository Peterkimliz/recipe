package com.example.favrecipe.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.favrecipe.databinding.FragmentRandomDishBinding
import com.example.favrecipe.viewmodels.NotificationsViewModel

class RandomDishFragment : Fragment() {
    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentRandomDishBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =ViewModelProvider(this).get(NotificationsViewModel::class.java)
        binding = FragmentRandomDishBinding.inflate(inflater, container, false)
        return binding.root
    }


}