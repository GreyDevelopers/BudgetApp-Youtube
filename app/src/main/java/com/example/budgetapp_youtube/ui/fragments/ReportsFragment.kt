package com.example.budgetapp_youtube.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetapp_youtube.R
import com.example.budgetapp_youtube.databinding.FragmentBudgetEntryBinding
import com.example.budgetapp_youtube.databinding.FragmentCalenderViewBinding
import com.example.budgetapp_youtube.databinding.FragmentReportsBinding
import com.example.budgetapp_youtube.databinding.UpdateBudgetBottomSheetBinding
import com.example.budgetapp_youtube.ui.adapter.ReportsAdapter
import com.example.budgetapp_youtube.ui.viewmodels.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportsFragment : Fragment(R.layout.fragment_reports), ReportsAdapter.MyOnClickListener {

    lateinit var binding: FragmentReportsBinding
    private val budgetViewModel:BudgetViewModel by viewModels()
    private lateinit var reportsAdapter: ReportsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportsBinding.bind(view)
        initializeRecyclerView()
        getAllEntries()

        binding.statistics.setOnClickListener {
            val bottomSheet = StatisticsBottomSheetFragment()
            bottomSheet.show(requireActivity().supportFragmentManager,"BottomSheet")

        }
    }

    private fun getAllEntries() {
        budgetViewModel.allBudgetEntries.observe(viewLifecycleOwner){
            reportsAdapter.differ.submitList(it)
        }
    }

    private fun initializeRecyclerView() {
        reportsAdapter = ReportsAdapter(this)
        binding.rcvReports.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reportsAdapter
        }

    }

    override fun OnClick(position: Int) {
        val currentBudgetItem = reportsAdapter.differ.currentList[position]
        val bottomSheet = UpdateBudgetBottomSheetFragment(currentBudgetItem)
        bottomSheet.show(requireActivity().supportFragmentManager,"UpdateBudget")

    }
}