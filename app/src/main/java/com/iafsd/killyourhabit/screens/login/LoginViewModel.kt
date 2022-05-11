package com.iafsd.killyourhabit.screens.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iafsd.killyourhabit.NavRoutes
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.getStateFlow
import com.iafsd.killyourhabit.repository.UserRepositoryImpl
import com.iafsd.killyourhabit.tools.Tools.showMeThread
import com.iafsd.killyourhabit.ui.common.FocusedTextFieldKey
import com.iafsd.killyourhabit.ui.common.InputValidator
import com.iafsd.killyourhabit.ui.common.InputWrapper
import com.iafsd.killyourhabit.ui.common.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
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
class LoginViewModel
    @Inject constructor(
        private val handle: SavedStateHandle,
        private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val TAG: String = LoginViewModel::class.java.simpleName

    val _email = handle.getStateFlow(viewModelScope, "email", InputWrapper())

    val _password = handle.getStateFlow(viewModelScope, "password", InputWrapper())

    //Check if input name, email, and credit card number valid
    val areInputsValid = combine(_password, _email) { password, email ->
        email.value.isNotEmpty() && email.errorId == null
                && password.value.isNotEmpty() && password.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


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

    //email entered
    fun onEmailEntered(input: String) {
        //Input validator
        val errorId = InputValidator.getEmailErrorIdOrNull(input)
        Log.wtf("F1", "input: $input, ")
        _email.value = _email.value.copy(value = input, errorId = errorId)
    }
    //Click on Background close the Keyboard
    fun onContinueBGClick() {
        viewModelScope.launch(Dispatchers.Default) {
            clearFocusAndHideKeyboard()
        }
    }

   fun onClickButtonLogin() {
       showMeThread("$TAG: onClickButtonLogin")
        viewModelScope.launch(Dispatchers.IO) {
            showMeThread("$TAG: onClickButtonLogin")
            _events.send(ScreenEvent.Loading(true))
             userRepositoryImpl.loginUserInRemoteDataBase2(
                _email.value.value,
                _password.value.value)
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Log.wtf(TAG, "signInWithEmail:successfully")
                    // Sign in success, update UI with the signed-in user's information
                    showMeThread("$TAG: signInWithEmail:successfully")
                    loginManager(true,"")

                } else {

                    loginManager(false, task.exception.toString())
                    // If sign in fails, display a message to the user.
                    Log.wtf(TAG, "signInWithEmail:failure", task.exception)
                }
            }
    }
   }
    private fun loginManager(boolean: Boolean, message:String){
        showMeThread("$TAG: loginManager")
        viewModelScope.launch(Dispatchers.Default) {
            if (boolean) successfulLogin() else faultToLogin(message)
        }
    }

    private suspend fun successfulLogin(){
        showMeThread("$TAG: successfulLogin")
        delay(300)
        Log.wtf(TAG, "signInWithEmail: successfulLogin")
        _events.send(ScreenEvent.ShowToast(R.string.successful_registered))
        _events.send(ScreenEvent.MoveToScreen(NavRoutes.HomeScreen.route))
        _events.send(ScreenEvent.Loading(false))

    }

    private suspend fun faultToLogin(faultMessage: String){
        showMeThread("$TAG: faultToLogin")
        delay(300)
        Log.wtf(TAG, "signInWithEmail: faultToLogin")
        _events.send(ScreenEvent.Loading(false))
        _events.send(ScreenEvent.ShowToastString(faultMessage))

    }

    //password entered
    fun onPasswordEntered(password: String) {
        //Input validator
        val errorId = InputValidator.getPasswordErrorIdOrNull(password.trim())
        _password.value = _password.value.copy(value = password.trim(), errorId = errorId)
    }

    fun onTextFieldFocusChanged(key: FocusedTextFieldKey, isFocused: Boolean) {
        focusedTextField = if (isFocused) key else FocusedTextFieldKey.NONE
    }

    fun onEmailImeActionClick() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.MoveFocus())
        }
    }

    fun onContinueClick() {
        viewModelScope.launch(Dispatchers.Default) {
            if (areInputsValid.value) clearFocusAndHideKeyboard()
        }
    }

    private suspend fun clearFocusAndHideKeyboard() {
        _events.send(ScreenEvent.ClearFocus)
        _events.send(ScreenEvent.UpdateKeyboard(false))
        focusedTextField = FocusedTextFieldKey.NONE
    }

    private fun focusOnLastSelectedTextField() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.RequestFocus(focusedTextField))
            delay(250)
            _events.send(ScreenEvent.UpdateKeyboard(true))
        }
    }

    fun onTextClickText() {
        viewModelScope.launch(Dispatchers.Default) {
            Log.wtf(TAG, "onTextClickText: GoToRegisterScreen")
            _events.send(ScreenEvent.MoveToScreen(NavRoutes.RegisterScreen.route))
        }
    }
  /*  withContext(Dispatchers.Default) {

    }.also {
        log.debug("Prime calculation took ${it.second} ms")
    }.first*/


}