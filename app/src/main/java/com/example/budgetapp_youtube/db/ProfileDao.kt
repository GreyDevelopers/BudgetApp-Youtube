package com.example.budgetapp_youtube.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.budgetapp_youtube.entities.Profile

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileData(profile: Profile)

    @Query("SELECT * FROM profile_table ORDER BY id DESC")
    fun getProfileData():LiveData<List<Profile>>

    @Query("UPDATE profile_table SET currentBalance = :revisedBalance")
    suspend fun updateCurrentBalance(revisedBalance:Float)
}