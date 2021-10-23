package com.example.budgetapp_youtube.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.budgetapp_youtube.R
import com.example.budgetapp_youtube.databinding.StatasticsBottomSheetBinding
import com.example.budgetapp_youtube.ui.viewmodels.BudgetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsBottomSheetFragment:BottomSheetDialogFragment() {

    private lateinit var binding: StatasticsBottomSheetBinding
    private val budgetViewModel:BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.statastics_bottom_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StatasticsBottomSheetBinding.bind(view)

        budgetViewModel.totalCredit.observe(viewLifecycleOwner){
            binding.totalCredit.text = it.toString()
        }
        budgetViewModel.totalSpendig.observe(viewLifecycleOwner){
            binding.totalSpending.text = (-1*it).toString()
        }
    }










}