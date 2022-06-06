package com.iafsd.killyourhabit.screens.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iafsd.killyourhabit.data.User
import com.iafsd.killyourhabit.navigation.NavRoutes
import com.iafsd.killyourhabit.repository.UserRepositoryImpl
import com.iafsd.killyourhabit.ui.common.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val userRepositoryImpl: UserRepositoryImpl,
    @ApplicationContext application: Context,
) : ViewModel() {

    private val TAG: String = "HomeViewModel"
    private val _events = Channel<ScreenEvent>()
    val events = _events.receiveAsFlow()
    val user :  MutableLiveData<User> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    init {
        checkIfUserAuth()
        initUserFromDataBase()
    }

   /* private fun intitAdmob(){
        //initialize the mobile ads sdk
        MobileAds.initialize(this) {}
        //load the interstitial ad
        loadInterstitial(this)
        //add the interstitial ad callbacks
        addInterstitialCallbacks(this)
    }*/


    private fun checkIfUserAuth() {
        viewModelScope.launch(Dispatchers.Default) {
            if (userRepositoryImpl.isUserAuth() == null) {
                _events.send(ScreenEvent.MoveToScreen(NavRoutes.LoginScreen.route))
            }
            else{
                _events.send(ScreenEvent.IsHomeScreenVisible(true))
            }
        }
    }
        private fun goToLoginScreen() {
            viewModelScope.launch(Dispatchers.Default) {
                _events.send(ScreenEvent.MoveToScreen(NavRoutes.LoginScreen.route))
            }
        }

        fun logout() {
            userRepositoryImpl.signOutUser().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    goToLoginScreen()
                },{ throwable ->
                    Log.i(TAG,
                        "logout: error :  ${throwable.message}")
                }).also { compositeDisposable.add(it) }

        }

        private fun initUserFromDataBase() {
            userRepositoryImpl.loadUserDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    Log.wtf(TAG, "initUserFromDataBase: fault : Error:  ${it.message}")
                }
                .doOnSuccess { Log.i(TAG, "initUserFromDataBase: Success:  $it") }
                .subscribe({ userR ->
                    user.postValue(userR)
                },
                    { throwable ->
                        Log.i(TAG,
                            "registerNewUserInRemoteDataBase: throwable :  ${throwable.message}")
                    })
                .also {
                    compositeDisposable.add(it)
                }

        }

        override fun onCleared() {
            super.onCleared()
            compositeDisposable.clear()

        }
}