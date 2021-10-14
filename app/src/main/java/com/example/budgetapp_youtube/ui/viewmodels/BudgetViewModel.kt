package com.example.budgetapp_youtube.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetapp_youtube.entities.Budget
import com.example.budgetapp_youtube.repositories.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    val budgetRepository: BudgetRepository
):ViewModel(){

    val allBudgetEntries : LiveData<List<Budget>> = budgetRepository.getAllBudgetEntries()

    fun insertBudget(budget: Budget) = viewModelScope.launch {
        budgetRepository.insertBudget(budget)
    }
}