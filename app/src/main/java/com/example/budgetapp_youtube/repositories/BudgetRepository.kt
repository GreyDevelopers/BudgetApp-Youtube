package com.example.budgetapp_youtube.repositories

import com.example.budgetapp_youtube.db.BudgetDao
import javax.inject.Inject

class BudgetRepository @Inject constructor(
    val budgetDao: BudgetDao
) {


}