package com.example.budgetapp_youtube.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.budgetapp_youtube.R
import com.example.budgetapp_youtube.databinding.FragmentBudgetEntryBinding
import com.example.budgetapp_youtube.databinding.FragmentCalenderViewBinding

class BudgetEntryFragment : Fragment(R.layout.fragment_budget_entry) {

    lateinit var binding: FragmentBudgetEntryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBudgetEntryBinding.bind(view)
    }


}