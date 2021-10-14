package com.example.budgetapp_youtube.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.budgetapp_youtube.R
import com.example.budgetapp_youtube.databinding.FragmentCalenderViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaledarViewFragment : Fragment(R.layout.fragment_calender_view) {

    lateinit var binding: FragmentCalenderViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalenderViewBinding.bind(view)
        setHasOptionsMenu(true)
        activity?.title = "Enter Your Budget"

        binding.calView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "${dayOfMonth}/${month + 1}/${year}"

            val action =
                CaledarViewFragmentDirections.actionCaledarViewFragmentToBudgetEntryFragment()
                    .setSelectedDate(selectedDate)
            findNavController().navigate(action)

        }

    }


}