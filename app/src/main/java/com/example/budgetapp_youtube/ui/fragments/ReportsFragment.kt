package com.example.budgetapp_youtube.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.budgetapp_youtube.R
import com.example.budgetapp_youtube.databinding.FragmentBudgetEntryBinding
import com.example.budgetapp_youtube.databinding.FragmentCalenderViewBinding
import com.example.budgetapp_youtube.databinding.FragmentReportsBinding

class ReportsFragment : Fragment(R.layout.fragment_reports) {

    lateinit var binding: FragmentReportsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportsBinding.bind(view)
    }


}