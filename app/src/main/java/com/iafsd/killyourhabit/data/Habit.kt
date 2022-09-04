package com.iafsd.killyourhabit.data

import com.iafsd.killyourhabit.tools.HabitStrengthStatus
import java.util.*

data class Habit(
   val id:String,
   var title: String,
   private var startDate: Date = Date(System.currentTimeMillis()),
   private var daysWithoutPause: Int = 0,
   private var daysMissed: Int = 0,
){

    fun getAllDays() :Int = 20

    fun getHabitStrengthStatus() =
        when(daysWithoutPause)
        {
            in 10..15 -> HabitStrengthStatus.Lower
            in 15..31 -> HabitStrengthStatus.Medium
            in 31..100 -> HabitStrengthStatus.Strength
            in 100..366 -> HabitStrengthStatus.Forever
            else -> HabitStrengthStatus.No
        }

    fun getDaysWithoutPause() = daysWithoutPause
}
