package com.iafsd.killyourhabit.ui.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class InputWrapper(
val value: String = "",
val errorId: Int? = null
) : Parcelable
