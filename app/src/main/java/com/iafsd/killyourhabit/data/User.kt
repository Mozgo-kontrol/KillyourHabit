package com.iafsd.killyourhabit.data


data class User(
    var id:String,
    var nickname: String,
    var email: String,
    var registerDate: Float,
    var birthdayDate: Float,
    var isEmailValidated: Boolean,
){
    constructor() : this("", "", "",1234243324F,1234243324F,false)
}




