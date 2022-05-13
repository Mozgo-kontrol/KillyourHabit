package com.iafsd.killyourhabit.screens.login

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.toast
import com.iafsd.killyourhabit.tools.GridUnit
import com.iafsd.killyourhabit.ui.common.*


@ExperimentalComposeUiApi
@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val TAG = "LoginScreen"
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current

    val focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }


    val email by viewModel._email.collectAsState()

    val password by viewModel._password.collectAsState()

    val areInputsValid by viewModel.areInputsValid.collectAsState()

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    val showPB = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is ScreenEvent.Loading -> showPB.value = event.show
                is ScreenEvent.ShowToast -> context.toast(event.messageId)
                is ScreenEvent.UpdateKeyboard -> {
                    if (event.show) keyboardController?.show() else keyboardController?.hide()
                }
                is ScreenEvent.ClearFocus -> focusManager.clearFocus()
                is ScreenEvent.RequestFocus -> {
                    when (event.textFieldKey) {
                        FocusedTextFieldKey.EMAIL -> emailFocusRequester.requestFocus()
                        FocusedTextFieldKey.PASSWORD -> passwordFocusRequester.requestFocus()
                        else -> {
                            Log.e(TAG, "ScreenEvent.RequestFocus -> None")
                        }
                    }
                }
                is ScreenEvent.MoveFocus -> focusManager.moveFocus(event.direction)
                is ScreenEvent.MoveToScreen -> {
                    navController.popBackStack()
                    navController.navigate(event.navRoutes)
                }
                else -> {
                    Log.e(TAG, "ScreenEvent -> None")
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().pointerInput(Unit) {
            detectTapGestures(onTap = {
                viewModel.onContinueBGClick()
            })
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showPB.value) {
            Card(
                modifier = Modifier
                    .defaultMinSize(minWidth = 64.dp, minHeight = 64.dp),
                elevation = 10.dp, shape = CircleShape
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.secondary,
                    strokeWidth = GridUnit.half.dp
                )
            }
            Spacer(Modifier.height(GridUnit.two.dp))
        } else {
            //Title
            Text(
                modifier = Modifier,
                text = stringResource(R.string.login_screen_title),
                style = MaterialTheme.typography.h2
            )

            //email
            KYHTextField(
                modifier = Modifier
                    .focusRequester(emailFocusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.EMAIL,
                            isFocused = focusState.isFocused
                        )
                    },
                labelResId = R.string.email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                inputWrapper = email,
                onValueChange = viewModel::onEmailEntered,
                onImeKeyAction = viewModel::onEmailImeActionClick
            )
            Spacer(Modifier.height(GridUnit.two.dp))
            //password
            KYHTextField(
                modifier = Modifier
                    .focusRequester(passwordFocusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.PASSWORD,
                            isFocused = focusState.isFocused
                        )
                    },
                labelResId = R.string.password,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                inputWrapper = password,
                onValueChange = viewModel::onPasswordEntered,
                onImeKeyAction = viewModel::onContinueClick,
                maxCharCount = 30,
                isPassword = true
            )
            Spacer(Modifier.height(GridUnit.four.dp))

            KYHButton(
                onClickButton = viewModel::onClickButtonLogin,
                isButtonEnabled = areInputsValid,
                shapeButton = MaterialTheme.shapes.medium,
                text = stringResource(R.string.login),
            )
            Spacer(Modifier.height(GridUnit.four.dp))

            KYHUnderlineText(
                textFirstContent = stringResource(R.string.if_do_not_have_an_account),
                textSecondContent = stringResource(com.iafsd.killyourhabit.R.string.dssd),
                onClickText = viewModel::onTextClickText
            )
        }
    }
}

