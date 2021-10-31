package com.example.budgetapp_youtube.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.example.budgetapp_youtube.util.UtilityFunctions
import com.example.budgetapp_youtube.util.UtilityFunctions.dateMillisToString
import com.example.budgetapp_youtube.util.UtilityFunctions.getEndDate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReportsFragment : Fragment(R.layout.fragment_reports), ReportsAdapter.MyOnClickListener {

    lateinit var binding: FragmentReportsBinding
    private val budgetViewModel:BudgetViewModel by viewModels()
    private lateinit var reportsAdapter: ReportsAdapter
    private val dateRangeArray = arrayOf("Select Date Range", "1 Week", "1 Month", "6 Month",
    "Show All")
    private lateinit var startDate:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportsBinding.bind(view)
        activity?.title = "Spending Reports"
        startDate = setStartDate()
        initializeRecyclerView()
        setSpinnerValues()
        getAllEntries()

        binding.statistics.setOnClickListener {
            val bottomSheet = StatisticsBottomSheetFragment()
            bottomSheet.show(requireActivity().supportFragmentManager,"BottomSheet")

        }
        binding.dateRangeReportSpinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(parent?.getItemAtPosition(position)){
                    "1 Week" -> getReportsBetweenDates(startDate, getEndDate(7))
                    "1 Month" -> getReportsBetweenDates(startDate, getEndDate(30))
                    "6 Month" -> getReportsBetweenDates(startDate, getEndDate(180))
                    "Show All" -> getAllEntries()

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    private  fun getReportsBetweenDates(startDate:String,endDate:String){
        val start = UtilityFunctions.dateStringToMillis(endDate)
        val end = UtilityFunctions.dateStringToMillis(startDate)


        budgetViewModel.getReportsBetweenDates(start,end)
        budgetViewModel.daateRangeBudgetEntries.observe(viewLifecycleOwner){
            reportsAdapter.differ.submitList(it)
        }
    }



    private fun setSpinnerValues() {
       val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,dateRangeArray)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dateRangeReportSpinner.adapter = arrayAdapter
    }

    private fun setStartDate(): String{
        val datInMillis = Calendar.getInstance().timeInMillis
        return dateMillisToString(datInMillis)
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