
package com.example.ayudafilosofica.feature.home.presentation

import retrofit2.HttpException
import java.io.IOException
import com.example.ayudafilosofica.domain.chat.usecase.GenerateBotReplySuspend
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ayudafilosofica.core.model.Menssage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.ayudafilosofica.domain.util.MessageTime
import com.example.ayudafilosofica.domain.util.IdGenerator
import java.net.SocketTimeoutException

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val idGen : IdGenerator,
    private val clock : MessageTime,
    private val bot: GenerateBotReplySuspend
) : ViewModel(){
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow() //Esto es un flujo observable
    //Esta en un canal separado, para que no se guarde en el state
    private val _effects = kotlinx.coroutines.flow.MutableSharedFlow<HomeEffect>(replay = 0, extraBufferCapacity = 1)
    val effects = _effects.asSharedFlow()
    //Necesitamos un ScrollToBotton, HideKEyBoard, ShowSnackBar

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.InputChanged -> {
                _state.update { it.reduce(event) }
            }

            //Revisar, no se asigna aqui el mensaje
            is HomeEvent.BotReplyArrived -> {
                _state.update {
                    it.reduce(event)
                }
                tryEmitEffect(HomeEffect.ScrollToBottom)
            }

            is HomeEvent.BotReplyFailed -> {
                //Faltaria el efecto, pero devemos saber el error del porque no llego
                _state.update { it.reduce(event) }
                emitEffect(HomeEffect.ShowSnackBar(event.reason))
            }

            is HomeEvent.SendClicked -> {
                val raw = state.value.inputText
                val text = raw.trim()
                if (state.value.isSending) {
                    return
                }
                if (text.isEmpty()) {
                    emitEffect(HomeEffect.ShowSnackBar("Escriba algo"))
                    return
                }
                val userMessage = prepareUserMessage(text)
                _state.update {
                    it.reduce(
                        HomeEvent.UserMessagePrepared(
                            message = userMessage
                        )
                    )
                }
                tryEmitEffect(HomeEffect.HideKeyboard)
                tryEmitEffect(HomeEffect.ScrollToBottom)

                viewModelScope.launch {
                    sendToBot(text)

                }
            }

            is HomeEvent.UserMessagePrepared -> {
                _state.update { it.reduce(event) }
            }
        }
    }

    //uso para efectos críticos (garantizar entrega), se hace emit dentro de un launch
    private fun emitEffect(effect: HomeEffect) {
        viewModelScope.launch { _effects.emit(effect) }
    }

    //uso para efectos no críticos (scroll/teclado), puede perderse si el buffer está lleno
    private fun tryEmitEffect(effect: HomeEffect){
        _effects.tryEmit(effect)
    }


    private fun prepareUserMessage(text: String): Menssage {
        return Menssage(
            id = idGen.nextId(),
            texto = text,
            deUsuario = true,
            timestamp = clock.currentTime()
        )
    }

    private fun prepareBotMessage(text: String): Menssage {
        return Menssage(
            id = idGen.nextId(),
            texto = text,
            deUsuario = false,
            timestamp = clock.currentTime()
        )
    }

    private suspend fun sendToBot(prompt: String) {
        val history = state.value.messages
        val result = bot.generateBotReply(prompt, history)
        result
            .onSuccess { botMsg -> onEvent(HomeEvent.BotReplyArrived(botMsg)) }
            .onFailure { e ->
                android.util.Log.e("BOT", "Fallo al llamar al bot", e)
                val msg = mapErrorToText(e)
                onEvent(HomeEvent.BotReplyFailed(msg.toString()))
            }
    }


    private fun mapErrorToText(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    400 -> "La petición no es válida. Revisa el mensaje enviado."
                    401, 403 -> "Problema con la API key. Revisa la configuración."
                    429 -> "Demasiadas peticiones. Espera unos segundos e inténtalo de nuevo."
                    500, 502, 503, 504 -> "Los servidores de la IA están ocupados. Inténtalo de nuevo en un momento."
                    else -> "Error del servidor (HTTP ${throwable.code()})."
                }
            }
            is SocketTimeoutException ->
                "La respuesta está tardando demasiado. Revisa tu conexión o inténtalo de nuevo."
            is IOException ->
                "Problema de conexión. Comprueba tu Internet."
            else -> throwable.message ?: "Error desconocido."
        }
    }

}

