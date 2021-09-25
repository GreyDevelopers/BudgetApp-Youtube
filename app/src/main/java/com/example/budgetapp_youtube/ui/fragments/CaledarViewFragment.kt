package com.example.budgetapp_youtube.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.budgetapp_youtube.R
import com.example.budgetapp_youtube.databinding.FragmentCalenderViewBinding

class CaledarViewFragment : Fragment(R.layout.fragment_calender_view) {

    lateinit var binding: FragmentCalenderViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalenderViewBinding.bind(view)
    }


}