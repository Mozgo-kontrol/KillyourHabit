package com.iafsd.killyourhabit.screens.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.iafsd.killyourhabit.data.User
import com.iafsd.killyourhabit.repository.UserRepositoryImpl
import com.iafsd.killyourhabit.ui.common.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val TAG: String = "HomeViewModel"
    private val _events = Channel<ScreenEvent>()
    val events = _events.receiveAsFlow()
    val user :  MutableLiveData<User> = MutableLiveData()

    lateinit var userId :String
    private val compositeDisposable = CompositeDisposable()
    init {
        initUserFromDataBase()
    }

     private fun initUserFromDataBase() {

        userRepositoryImpl.loadUserDetails()
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.wtf(TAG, "initUserFromDataBase: fault : Error:  ${it.message}")
            }
            .doOnSuccess { Log.i(TAG, "initUserFromDataBase: Success:  $it") }
            .subscribe({ userR ->
                    user.value = userR
            },
                { throwable -> Log.i(TAG, "registerNewUserInRemoteDataBase: throwable :  ${throwable.message}") })
            .also {
                compositeDisposable.add(it)
            }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()

    }


}