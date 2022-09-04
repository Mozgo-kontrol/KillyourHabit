package com.iafsd.killyourhabit.network.retrofit.v2

import com.iafsd.killyourhabit.data.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule @Inject constructor() {

    @Provides
    fun provideNetworkService(): NetworkService {  // Potential dependencies of this type
        return Retrofit.Builder()
            .baseUrl("https://devicefinder-server-v1-default-rtdb.europe-west1.firebasedatabase.app/") //url of firebase app
            .addConverterFactory(GsonConverterFactory.create())     //use for convert JSON file into object
            .build()
            .create(NetworkService::class.java)
    }

}

interface NetworkService {
    // create new user
    @POST("/{users}.json")
    fun createUser(@Path("users") s1: String?, @Body user: User?) : Completable

    @GET("/users/{userId}.json")
    fun getUserById(@Path("userId") s1: String): Single<User?>

    @PATCH("/users/{deviceId}.json")
    fun updateUserInfoById(@Path("users") s1: String?, @Body user: User?) : Completable


}