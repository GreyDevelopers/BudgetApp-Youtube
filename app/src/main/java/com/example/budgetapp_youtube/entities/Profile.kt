package com.example.budgetapp_youtube.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "profile_table"
)
data class Profile(
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,
    val name:String,
    val email:String,
    val profileImageFilePath:String,
    val bankName:String,
    val currentBalance:Float,
    val initialBalance:Float,
    val primaryBank:Boolean
)
