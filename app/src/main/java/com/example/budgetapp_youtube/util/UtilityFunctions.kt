package com.example.budgetapp_youtube.util

import java.text.SimpleDateFormat
import java.util.*

object UtilityFunctions {

    fun dateStringToMillis(dateInString:String):Long{
        val  dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.parse(dateInString)
        return date.time
    }

    fun dateMillisToString(dateInMillis:Long):String{
        val  dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val cal = Calendar.getInstance()
        cal.timeInMillis = dateInMillis
        return dateFormat.format(cal.time)
    }
}