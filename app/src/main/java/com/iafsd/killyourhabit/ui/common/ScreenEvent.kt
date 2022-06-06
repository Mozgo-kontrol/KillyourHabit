package com.iafsd.killyourhabit.ui.common

import androidx.compose.ui.focus.FocusDirection

sealed class ScreenEvent{
    class ShowToastString(val message: String) : ScreenEvent()
    class ShowToast(val messageId: Int) : ScreenEvent()
    class UpdateKeyboard(val show: Boolean) : ScreenEvent()
    class Loading(val show: Boolean) : ScreenEvent()
    class RequestFocus(val textFieldKey: FocusedTextFieldKey) : ScreenEvent()
    object ClearFocus : ScreenEvent()
    class MoveFocus(val direction: FocusDirection = FocusDirection.Down) : ScreenEvent()
    class MoveToScreen(val navRoutes: String) : ScreenEvent()
    class IsHomeScreenVisible (val istVisible: Boolean) : ScreenEvent()
}
