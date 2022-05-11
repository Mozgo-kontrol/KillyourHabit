package com.iafsd.killyourhabit.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iafsd.killyourhabit.data.User
import com.iafsd.killyourhabit.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 *
 * VieModel class responsible for business logic in the App
 *
 * and word LifeData class
 *
 *
 * */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
)  : ViewModel(){

    private val TAG: String = UserViewModel::class.java.simpleName
    private val compositeDisposable = CompositeDisposable()

    private val user : MutableLiveData<User> = MutableLiveData()

    init {

    }


    fun createNewUserInFIreBase(email:String,password:String){



    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()

    }

}