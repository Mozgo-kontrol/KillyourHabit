package com.iafsd.killyourhabit.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.iafsd.killyourhabit.data.User
import com.iafsd.killyourhabit.network.firebase.FireBaseDataSource
import com.iafsd.killyourhabit.network.retrofit.ApiService
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

// Scope this class to a component using @Singleton scope (i.e. ApplicationGraph)
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val fireBaseDataSource: FireBaseDataSource
) : UserRepository{
    override fun registerNewUserInRemoteDataBase(email: String, password: String): Single<String> {
        return fireBaseDataSource.createUserWithEmailAndPassword(email,password)
    }

    override fun loginUserInRemoteDataBase(email: String, password: String): Single<FirebaseUser> {
        return fireBaseDataSource.signInWithEmailAndPassword(email,password)
    }

    override suspend fun loginUserInRemoteDataBase2(email: String, password: String): Task<AuthResult> {
        return fireBaseDataSource.signInWithEmailAndPassword2(email,password)
    }

    override fun loadUserDetails(): Single<User?> {
        return fireBaseDataSource.getUserById()
    }

    override fun signOutUser(): Completable {
        return fireBaseDataSource.signOut()
    }


}

