package com.iafsd.killyourhabit.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.iafsd.killyourhabit.data.User
import io.reactivex.Completable
import io.reactivex.Single


interface UserRepository {
    fun registerNewUserInRemoteDataBase(email :String, password :String): Single<String>
    fun loginUserInRemoteDataBase(email :String, password :String): Single<FirebaseUser>
    suspend fun loginUserInRemoteDataBase2 (email :String, password :String): Task<AuthResult>
    fun loadUserDetails() : Single<User?>
    fun signOutUser(): Completable
    fun isUserAuth(): FirebaseUser?
}