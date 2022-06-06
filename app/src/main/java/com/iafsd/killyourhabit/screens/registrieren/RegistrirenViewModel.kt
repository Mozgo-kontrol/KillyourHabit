package com.iafsd.killyourhabit.screens.registrieren

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.getStateFlow
import com.iafsd.killyourhabit.navigation.NavRoutes
import com.iafsd.killyourhabit.repository.UserRepositoryImpl
import com.iafsd.killyourhabit.ui.common.FocusedTextFieldKey
import com.iafsd.killyourhabit.ui.common.InputValidator
import com.iafsd.killyourhabit.ui.common.InputWrapper
import com.iafsd.killyourhabit.ui.common.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrirenViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val TAG: String = "RegistrirenViewModel"

    private val compositeDisposable = CompositeDisposable()

    val _email = handle.getStateFlow(viewModelScope, "email", InputWrapper())

    val _password = handle.getStateFlow(viewModelScope, "password", InputWrapper())

    val _confirmPassword = handle.getStateFlow(viewModelScope, "confirmPassword", InputWrapper())


    //Check if input name, email, and credit card number valid
    val areInputsValid =
        combine(_email, _password, _confirmPassword) { email, password, confirmPassword ->
            email.value.isNotEmpty() && email.errorId == null
                    && password.value.isNotEmpty() && password.errorId == null
                    && confirmPassword.value.isNotEmpty() && confirmPassword.errorId == null
                    && password.value == confirmPassword.value
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun onCleared() {
        super.onCleared()
        // handle["email"] = _email
        // handle["password"] = _password
        //  handle["confirmPassword"] = _confirmPassword
        compositeDisposable.clear()
    }

    // set focusedTextField
    private var focusedTextField = handle.get("focusedTextField") ?: FocusedTextFieldKey.EMAIL
        set(value) {
            field = value
            handle.set("focusedTextField", value)
        }

    private val _events = Channel<ScreenEvent>()
    val events = _events.receiveAsFlow()

    //initial focused Text Field in not NONE than last focus screen
    init {
        if (focusedTextField != FocusedTextFieldKey.NONE) focusOnLastSelectedTextField()
    }

    private fun focusOnLastSelectedTextField() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.RequestFocus(focusedTextField))
            delay(250)
            _events.send(ScreenEvent.UpdateKeyboard(true))
        }
    }
    //Click on Continue close the Keyboard id input is valid
    fun onContinueClick() {
        viewModelScope.launch(Dispatchers.Default) {
            if (areInputsValid.value) clearFocusAndHideKeyboard()
        }

    }

    //Click on Background close the Keyboard
    fun onContinueBGClick() {
        viewModelScope.launch(Dispatchers.Default) {
            clearFocusAndHideKeyboard()
        }
    }


    @SuppressLint("CheckResult")
    fun onButtonClick() {
        userRepositoryImpl.registerNewUserInRemoteDataBase(
            _email.value.value,
            _password.value.value
        ).subscribeOn(Schedulers.io()).doOnSubscribe {
            viewModelScope.launch(Dispatchers.Default) {
                _events.send(ScreenEvent.Loading(true))
            }
        }
            .doOnError {
                Log.wtf(TAG, "registerNewUserInRemoteDataBase: fault : Error:  ${it.message}")
            }
            .doOnSuccess { Log.i(TAG, "registerNewUserInRemoteDataBase: Success:  $it") }
            .subscribe({
                Log.i(TAG, "registerNewUserInRemoteDataBase: userId :  $it")
                registerManager(true, "")
            },
                { throwable -> registerManager(false, "${throwable.message}") }).also {
                compositeDisposable.add(it)
            }
    }

    private fun registerManager(isSuccess: Boolean, message: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (isSuccess) successRegistered() else faultRegistered(message)
        }

    }

    private suspend fun successRegistered() {
        // delay(300)
        _events.send(ScreenEvent.ShowToast(R.string.successful_registered))
        _events.send(ScreenEvent.MoveToScreen(NavRoutes.HomeScreen.route))
        _events.send(ScreenEvent.Loading(false))
    }

    private suspend fun faultRegistered (message: String) {

        delay(300)
        _events.send(ScreenEvent.Loading(false))
        Log.i(TAG, " faultRegistered: $message")
        _events.send(ScreenEvent.ShowToastString(message))

    }

    private suspend fun clearFocusAndHideKeyboard() {
        _events.send(ScreenEvent.ClearFocus)
        _events.send(ScreenEvent.UpdateKeyboard(false))
        focusedTextField = FocusedTextFieldKey.NONE
    }

    fun onTextFieldFocusChanged(key: FocusedTextFieldKey, isFocused: Boolean) {
        focusedTextField = if (isFocused) key else FocusedTextFieldKey.NONE
    }


    fun onEmailImeActionClick() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.MoveFocus())
        }
    }

    fun onPasswordImeActionClick() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.MoveFocus())
        }
    }


    //email entered
    fun onEmailEntered(input: String) {
        //Input validator
        val errorId = InputValidator.getEmailErrorIdOrNull(input.trim())
        _email.value = _email.value.copy(value = input.trim(), errorId = errorId)
    }

    //password entered
    fun onPasswordEntered(password: String) {
        //Input validator
        val errorId = InputValidator.getPasswordErrorIdOrNull(password.trim())
        _password.value = _password.value.copy(value = password.trim(), errorId = errorId)
    }

    //confirm password
    fun onConfirmPasswordEntered(confirmPassword: String) {
        val password = _password.value.value
        val errorId =
            InputValidator.getConfirmPasswordErrorIdOrNull(confirmPassword.trim(), password.trim())
        _confirmPassword.value =
            _confirmPassword.value.copy(value = confirmPassword.trim(), errorId = errorId)
    }

    fun onTextClickText() {
        viewModelScope.launch(Dispatchers.Default) {
            Log.wtf(TAG, "onTextClickText: GoToLoginScreen")
            _events.send(ScreenEvent.MoveToScreen(NavRoutes.LoginScreen.route))
        }
    }

}