package com.example.budgetapp_youtube.repositories

import com.example.budgetapp_youtube.db.ProfileDao
import com.example.budgetapp_youtube.entities.Profile
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    val profileDao : ProfileDao
){

    fun getProfile() = profileDao.getProfileData()

    suspend fun insertProfileDate(profile:Profile) = profileDao.insertProfileData(profile)
}