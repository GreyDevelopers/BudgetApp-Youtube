package com.example.budgetapp_youtube.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val totalCredit:LiveData<Float> = budgetRepository.getTotalCredit()
    val totalSpendig:LiveData<Float> = budgetRepository.getTotalSpending()

    var _daateRangeBudgetEntries : MutableLiveData<List<Budget>> = MutableLiveData()
    val daateRangeBudgetEntries : LiveData<List<Budget>> = _daateRangeBudgetEntries


    fun insertBudget(budget: Budget) = viewModelScope.launch {
        budgetRepository.insertBudget(budget)
    }

    fun updateBudget(amount:Float,purpose:String,id:Int) = viewModelScope.launch {
        budgetRepository.updateBudget(amount, purpose, id)
    }

    fun getReportsBetweenDates(startDate:Long, endDate:Long) = viewModelScope.launch {
        val reponse = budgetRepository.getBudgetEntriesBetweenDates(startDate, endDate)
        _daateRangeBudgetEntries.postValue(reponse)
    }

}