
package com.example.ayudafilosofica.feature.home.presentation

fun HomeState.reduce(event: HomeEvent): HomeState {
    return when (event) {
        is HomeEvent.InputChanged ->
            copy(
                inputText = event.text,
                canSend = event.text.trim().isNotEmpty() && !isSending
            )

        is HomeEvent.UserMessagePrepared ->
            copy(
                messages = messages + event.message,
                inputText = "",
                canSend = false,
                isSending = true
            )

        is HomeEvent.BotReplyArrived ->{
            val nextIsSending = false
            copy(
                messages = messages + event.message,
                isSending = false,
                canSend = inputText.trim().isNotEmpty() && !nextIsSending
            )
        }

        is HomeEvent.BotReplyFailed ->{
            val nextIsSending = false
            copy(
                isSending = false,
                canSend = inputText.trim().isNotEmpty() && !nextIsSending
            )
        }

        HomeEvent.SendClicked -> this
    }
}
