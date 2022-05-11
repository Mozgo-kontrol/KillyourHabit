package com.iafsd.killyourhabit.repository

import com.iafsd.killyourhabit.data.User

interface Repository {
    fun registerNewUser(email :String, password :String): String
    fun loginUser(email :String, password :String)
    fun loadUserDetails() : User
}