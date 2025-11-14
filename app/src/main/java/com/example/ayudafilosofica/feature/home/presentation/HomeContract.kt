
package com.example.ayudafilosofica.feature.home.presentation

import com.example.ayudafilosofica.core.model.Menssage

data class HomeState(
    val messages: List<Menssage> = emptyList(),
    val inputText: String = "",   //debemos darle valors por defecto para iniciarlos facilmente en el ViewModel
    val isSending: Boolean = false,
    val canSend: Boolean = false
)

sealed interface HomeEvent {
    data class InputChanged(val text: String) : HomeEvent
    data object SendClicked : HomeEvent

    //Eventos internos
    data class BotReplyArrived(val message: Menssage) : HomeEvent
    data class BotReplyFailed(val reason: String) : HomeEvent
    data class UserMessagePrepared(val message: Menssage) : HomeEvent
}

sealed interface HomeEffect {
    data class ShowSnackBar(val text: String) : HomeEffect
    data object ScrollToBottom : HomeEffect
    data object HideKeyboard : HomeEffect
}
