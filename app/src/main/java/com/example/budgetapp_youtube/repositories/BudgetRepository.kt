package com.example.budgetapp_youtube.repositories

import com.example.budgetapp_youtube.db.BudgetDao
import com.example.budgetapp_youtube.entities.Budget
import javax.inject.Inject

class BudgetRepository @Inject constructor(
    val budgetDao: BudgetDao
) {

    suspend fun insertBudget(budget:Budget) = budgetDao.insertBudget(budget)

    fun getAllBudgetEntries() = budgetDao.getAllData()

    suspend fun updateBudget(amount:Float, purpose:String,id:Int) = budgetDao.updateBudget(amount, purpose, id)

    suspend fun deleteEntry(budget: Budget) = budgetDao.deleteEntry(budget)


}