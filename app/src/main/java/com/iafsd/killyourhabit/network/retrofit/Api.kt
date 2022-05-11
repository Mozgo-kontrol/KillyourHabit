package com.iafsd.killyourhabit.network.retrofit

import com.iafsd.killyourhabit.data.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface Api {

    // create new user
    @POST("/{users}.json")
    fun createUser(@Path("users") s1: String?, @Body user: User?) : Completable


    @GET("/users/{userId}.json")
    fun getUserById(@Path("userId") s1: String): Single<User?>

    @PATCH("/users/{deviceId}.json")
    fun updateUserInfoById(@Path("users") s1: String?, @Body user: User?) : Completable

   // @POST("users/{id}.json")
 //   fun setDataWithoutRandomness(@Path("new") s1: String?, @Body user: User?): Call<User?>?
    // createNewObjekt
/*
    @PUT("/devices/{deviceId}.json")
    fun updateDevice(@Path("deviceId") s1: String?, @Body device: Device?): Call<Device?>?

    @GET("/devices.json")
    fun getListOfDevice(): Call<HashMap<String,Device>>

    @GET("/devices/{deviceId}.json")
    fun getDeviceById(@Path("deviceId") s1: String): Call<Device>*/
}