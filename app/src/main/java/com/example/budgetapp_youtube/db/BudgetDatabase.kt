package com.example.budgetapp_youtube.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budgetapp_youtube.entities.Budget
import com.example.budgetapp_youtube.entities.Profile

@Database(
    entities = [Budget::class,Profile::class],
    version = 1
)
abstract class BudgetDatabase:RoomDatabase() {
    abstract fun getBudgetDao():BudgetDao
    abstract fun getProfileDao():ProfileDao
}