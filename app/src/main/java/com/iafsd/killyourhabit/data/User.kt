package com.iafsd.killyourhabit.data



data class User(
    val id:String,
    val nickname: String,
    val email: String,
    val registerDate: Long,
    val birthdayDate: Long,
    val isEmailValidated: Boolean,

)

