
package com.example.ayudafilosofica.feature.home.presentation

fun HomeState.reduce(event: HomeEvent): HomeState =
    when (event) {
        is HomeEvent.InputChanged ->
            copy(
                inputText = event.text,
                canSend = event.text.trim().isNotEmpty()
            )

        is HomeEvent.UserMessagePrepared ->
            copy(
                messages = messages + event.message,
                inputText = "",
                canSend = false,
                isSending = true
            )

        is HomeEvent.BotReplyArrived ->
            copy(
                messages = messages + event.message,
                isSending = false
            )

        is HomeEvent.BotReplyFailed ->
            copy(
                isSending = false
            )

        HomeEvent.SendClicked -> this
    }
