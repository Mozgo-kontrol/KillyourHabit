package com.iafsd.killyourhabit.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun KYHTextField(
    modifier: Modifier,
    inputWrapper: InputWrapper,
    @StringRes labelResId: Int,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: OnValueChange,
    onImeKeyAction: OnImeKeyAction,
    maxCharCount: Int? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    isPassword: Boolean = false
) {
    val fieldValue = remember {
        mutableStateOf(TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length)))
    }

    var passwordCurrentHidden by rememberSaveable {
        if (isPassword) mutableStateOf(true) else mutableStateOf(false)
    }

    val focus = remember { mutableStateOf(false) }

    Column {
        TextField(
            modifier = modifier.onFocusChanged {
                if (focus.value != it.isFocused) {
                    focus.value = it.isFocused
                }
            },
            value = fieldValue.value,
            onValueChange = {
                if (maxCharCount != null) {
                    if (it.text.length < maxCharCount) {
                        fieldValue.value = it
                        onValueChange(it.text)
                    }
                } else {
                    fieldValue.value = it
                    onValueChange(it.text)
                }
            },
            label = { Text(stringResource(labelResId)) },
            isError = !focus.value && inputWrapper.errorId != null,
            visualTransformation = if (passwordCurrentHidden) PasswordVisualTransformation() else visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onAny = { onImeKeyAction() }),
            shape = shape,
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordCurrentHidden = !passwordCurrentHidden }) {
                        val visibilityIcon =
                            if (passwordCurrentHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        // Please provide localized description for accessibility services
                        val description =
                            if (passwordCurrentHidden) "Show password" else "Hide password"
                        Icon(imageVector = visibilityIcon, contentDescription = description)
                    }
                }
            }
        )
        if (!focus.value && inputWrapper.errorId != null) {
            Text(
                text = stringResource(inputWrapper.errorId),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}


