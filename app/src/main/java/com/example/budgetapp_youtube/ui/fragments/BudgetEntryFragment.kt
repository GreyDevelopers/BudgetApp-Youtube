package com.example.budgetapp_youtube.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.budgetapp_youtube.R
import com.example.budgetapp_youtube.databinding.FragmentBudgetEntryBinding
import com.example.budgetapp_youtube.entities.Budget
import com.example.budgetapp_youtube.ui.viewmodels.BudgetViewModel
import com.example.budgetapp_youtube.ui.viewmodels.ProfileViewModel
import com.example.budgetapp_youtube.util.UtilityFunctions.dateStringToMillis
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BudgetEntryFragment : Fragment(R.layout.fragment_budget_entry) {

    lateinit var binding: FragmentBudgetEntryBinding
    val args: BudgetEntryFragmentArgs by navArgs()
    private val profileViewModel: ProfileViewModel by viewModels()
    private var currentBalance: Float = 0.0f
    private lateinit var bankName: String
    private lateinit var debitOrCredit: String
    private lateinit var remainingBalance: String
    private val budgetViewModel: BudgetViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBudgetEntryBinding.bind(view)
        activity?.title = "Enter budget for: ${args.selectedDate}"

        getProfileDate()

        setSpinnerForDebitOrCredit()

        binding.bankSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                bankName = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                bankName = "NONE"
            }
        }

        binding.debitCreditSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    debitOrCredit = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    debitOrCredit = "Debit"
                }
            }

        binding.editAmount.addTextChangedListener { it ->
            it.let {
                val enteredAmount = it.toString()

                val temp = if (debitOrCredit.equals("Debit")) {
                    (currentBalance - enteredAmount.toFloat())
                } else {
                    (currentBalance + enteredAmount.toFloat())
                }
                remainingBalance = temp.toString()
                binding.remainingBalance.text = remainingBalance
            }
        }

        binding.submitBudgetEntry.setOnClickListener {
            val amount = binding.editAmount.text.toString()
            val purpose = binding.editPurpose.text.toString()
            val date = dateStringToMillis(args.selectedDate!!).toString()
            val revisedCurrentBalance = remainingBalance

            submitBudgetEntryToDB(
                bankName,
                debitOrCredit,
                amount,
                purpose,
                date,
                revisedCurrentBalance
            )
        }

    }

    private fun submitBudgetEntryToDB(
        bankName: String,
        debitOrCredit: String,
        amount: String,
        purpose: String,
        date: String,
        revisedCurrentBalance: String
    ) {
        var amountToInsert = amount.toFloat()
        if (debitOrCredit.equals("Debit")){
            amountToInsert = -1*amountToInsert
        }


        budgetViewModel.insertBudget(
            Budget(
                date = date,
                bankName = bankName,
                amount = amountToInsert,
                purpose =  purpose,
                creditOrDebit = debitOrCredit
            )
        )

        profileViewModel.updateCurrentBalance(revisedBalance = revisedCurrentBalance.toFloat())
        Snackbar.make(binding.budgetEntryConstraint,"Entry added",Snackbar.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_budgetEntryFragment_to_caledarViewFragment)

    }

    private fun setSpinnerForDebitOrCredit() {
        val debitOrCreditArray = ArrayList<String>()
        debitOrCreditArray.add("Debit")
        debitOrCreditArray.add("Credit")
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, debitOrCreditArray)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.debitCreditSpinner.adapter = arrayAdapter
    }

    private fun getProfileDate() {
        profileViewModel.profileLiveData.observe(viewLifecycleOwner) {
            val bankNames = ArrayList<String>()
            bankNames.add(it[0].bankName)
            currentBalance = it[0].currentBalance
            binding.remainingBalance.text = it[0].currentBalance.toString()
            val arrayAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, bankNames)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.bankSpinner.adapter = arrayAdapter
        }
    }


}