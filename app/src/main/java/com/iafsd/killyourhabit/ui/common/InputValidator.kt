package com.iafsd.killyourhabit.ui.common

import com.iafsd.killyourhabit.R

object InputValidator {
    private const val EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
    private const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
      //validate name
    fun getNameErrorIdOrNull(input: String): Int? {
        return when {
            input.length < 2 -> R.string.name_too_short
            //etc..
            else -> null
        }
    }
    //validate email
    private fun isEmailValid(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email);
    }

    //validate password
    private fun isPasswordValid(password: String): Boolean {
        return PASSWORD_REGEX.toRegex().matches(password)&& password.count() > 6
    }

    fun getEmailErrorIdOrNull(input: String): Int? {
        return when {
            !isEmailValid(input) -> R.string.email_invalid
            input.contains(" ") -> R.string.email_invalid_data
            //etc..
            else -> null
        }
    }

    fun getConfirmPasswordErrorIdOrNull(input: String, input2: String): Int? {
        return when {
            !isPasswordValid(input) -> R.string.password_invalid
             input!= input2 ->  R.string.password_not_the_same
            //etc..
            else -> null
        }
    }
    fun getPasswordErrorIdOrNull(input: String): Int? {
        return when {
            !isPasswordValid(input) -> R.string.password_invalid
            //etc..
            else -> null
        }
    }
    //validate cardNumber
    fun getCardNumberErrorIdOrNull(input: String): Int? {
        return when {
            input.length < 16 -> R.string.card_number_too_short
            //etc..
            else -> null
        }
    }

}