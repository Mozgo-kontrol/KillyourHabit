package com.iafsd.killyourhabit.screens.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iafsd.killyourhabit.data.PushMessage
import com.iafsd.killyourhabit.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val userRepositoryImpl: UserRepositoryImpl,
) : ViewModel() {

     val messageList: MutableLiveData<List<PushMessage>> = MutableLiveData(emptyList())

    init {
        getMyPushMessages()
    }
    private fun getMyPushMessages() {
        viewModelScope.launch(Dispatchers.Default) {
            val list = mutableListOf<PushMessage>()
            val oneMessage = PushMessage(2132431,
                "Guten Morgen",
                "So hast du mehr Energie und gute Laune für den Rest des Tages",
                "2.09.2022")
            for (i in 0..100) {
                list.add(oneMessage)
            }
            messageList.postValue(list)
        }
    }


}