package com.iafsd.killyourhabit.network.retrofit

import com.iafsd.killyourhabit.data.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService @Inject constructor() {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://devicefinder-server-v1-default-rtdb.europe-west1.firebasedatabase.app/") //url of firebase app
        .addConverterFactory(GsonConverterFactory.create()) //use for convert JSON file into object
        .build()

    private var api: Api = retrofit.create(Api::class.java) //use of interface

    fun createNewUserInDataBase(user: User) : Completable  {
        val call: Completable = api.createUser("users", user)
        return api.createUser("users", user)
    }

    fun getUserById(userId: String): Single<User?> {
        return api.getUserById(userId)
    }

    fun updateUserInfoById(user: User):  Completable {
        val userId = user.id.toString()
        return api.updateUserInfoById(userId,user)
    }
}