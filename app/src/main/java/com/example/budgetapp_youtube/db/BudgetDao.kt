package com.example.budgetapp_youtube.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.budgetapp_youtube.entities.Budget

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBudget(budget: Budget)

    @Query("SELECT * FROM budget ORDER BY id DESC")
    fun getAllData():LiveData<List<Budget>>

    @Query("UPDATE budget SET amount = :amount, purpose = :purpose WHERE id = :id")
    suspend fun  updateBudget(amount:Float, purpose:String, id:Int)

    @Query("SELECT SUM(amount) From budget WHERE creditOrDebit = 'Credit'")
    fun getTotalCredit():LiveData<Float>

    @Query("SELECT SUM(amount) from budget WHERe creditOrDebit = 'Debit'")
    fun getTotalSpendig():LiveData<Float>
}