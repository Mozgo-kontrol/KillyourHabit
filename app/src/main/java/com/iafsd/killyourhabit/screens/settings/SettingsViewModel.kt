package com.iafsd.killyourhabit.screens.settings

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iafsd.killyourhabit.admob.AdMob.addInterstitialCallbacks
import com.iafsd.killyourhabit.admob.AdMob.showInterstitial
import com.iafsd.killyourhabit.data.User
import com.iafsd.killyourhabit.navigation.NavRoutes
import com.iafsd.killyourhabit.repository.UserRepositoryImpl
import com.iafsd.killyourhabit.ui.common.CustomGlobal
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
class SettingsViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val userRepositoryImpl: UserRepositoryImpl,
    @ApplicationContext application: Context
): ViewModel() {
    private val TAG: String = "SettingsViewModel"

    private  var isADMobShown = false

    private val _events = Channel<ScreenEvent>()
    val events = _events.receiveAsFlow()
    private val compositeDisposable = CompositeDisposable()
    val user: MutableLiveData<User> = MutableLiveData()
    val isUserAuth: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            ifUserAuth()
            initUserFromDataBase()
        }

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

    fun logout() {
        userRepositoryImpl.signOutUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                goToLoginScreen()
            }, { throwable ->
                Log.wtf(TAG,
                    "logout: error :  ${throwable.message}")
            }).also { compositeDisposable.add(it) }

    }

    private fun ifUserAuth() {
        isUserAuth.postValue(userRepositoryImpl.isUserAuth())
    }

    private fun goToLoginScreen() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.MoveToScreen(NavRoutes.LoginScreen.route))
        }
    }

     fun showADMob(context: Context){
         if (!isADMobShown && CustomGlobal.isAdsOn){
             //loadInterstitial(context)
             addInterstitialCallbacks(context)
             showInterstitial(context)
             isADMobShown = true
         }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()

    }
}