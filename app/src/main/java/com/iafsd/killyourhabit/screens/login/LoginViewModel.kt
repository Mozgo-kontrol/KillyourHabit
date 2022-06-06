package com.iafsd.killyourhabit.screens.login

import android.annotation.SuppressLint
import android.content.Context

import android.util.Log

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.getStateFlow
import com.iafsd.killyourhabit.navigation.NavRoutes
import com.iafsd.killyourhabit.repository.UserRepositoryImpl
import com.iafsd.killyourhabit.tools.Tools.showMeThread
import com.iafsd.killyourhabit.ui.common.FocusedTextFieldKey
import com.iafsd.killyourhabit.ui.common.InputValidator
import com.iafsd.killyourhabit.ui.common.InputWrapper
import com.iafsd.killyourhabit.ui.common.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel
    @Inject constructor(
        private val handle: SavedStateHandle,
        private val userRepositoryImpl: UserRepositoryImpl,
        @ApplicationContext val application: Context
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

    //Click on Background close the Keyboard
    fun onContinueBGClick() {
        viewModelScope.launch(Dispatchers.Default) {
            clearFocusAndHideKeyboard()
        }
    }

   fun onClickButtonLogin() {

        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.Loading(true))
             userRepositoryImpl.loginUserInRemoteDataBase2(
                _email.value.value,
                _password.value.value)
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Log.wtf(TAG, "signInWithEmail:successfully")
                    // Sign in success, update UI with the signed-in user's information
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
        _events.send(ScreenEvent.MoveToScreen(NavRoutes.BottomNavigation.route))
        _events.send(ScreenEvent.Loading(false))

    }

    private suspend fun faultToLogin(faultMessage: String){
        delay(300)
        _events.send(ScreenEvent.Loading(false))

        _events.send(ScreenEvent.ShowToastString(faultMessage.split(":")[1]))

        val s = faultMessage.split(":")[1]
        faultMessage(s)
        Log.wtf(TAG, "faultToLogin: faultToLogin $s ")

    }
    //password entered
    //TODO
    private suspend fun faultMessage(message: String) {

         when {
             message.contains(application.getString(R.string.password_is_invalid)) -> {
                 _password.value = _password.value.copy(errorId = R.string.password_is_invalid)
             }
             message.contains(application.getString(R.string.no_user_record_corresponding)) -> {
                 _email.value = _email.value.copy(errorId = R.string.email_is_not_found)
             }
             message.contains(application.getString(R.string.try_again_later)) -> {
                 _email.value = _email.value.copy(errorId =  R.string.your_account_ist_blocked)
             }
             else -> {
             }
         }
    }

    fun onPasswordEntered(password: String) {
        //Input validator
        val errorId = InputValidator.getPasswordErrorIdOrNullLogin(password.trim())
        _password.value = _password.value.copy(value = password.trim(), errorId = errorId)
    }

    //email entered
    fun onEmailEntered(input: String) {
        //Input validator
        val errorId = InputValidator.getEmailErrorIdOrNull(input.trim())
        Log.wtf("F1", "input: $input, ")
        _email.value = _email.value.copy(value = input, errorId = errorId)
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