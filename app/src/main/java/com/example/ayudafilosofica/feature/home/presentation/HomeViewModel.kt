
package com.example.ayudafilosofica.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ayudafilosofica.core.Menssage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class HomeViewModel : ViewModel() {
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
                emitEffect(HomeEffect.ShowSnackBar("La maquina fallo"))
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
            id = AutoId.nextId(),
            texto = text,
            deUsuario = true,
            timestamp = TimeGenerator.currentTime()
        )
    }

    private fun prepareBotMessage(text: String): Menssage {
        return Menssage(
            id = AutoId.nextId(),
            texto = text,
            deUsuario = false,
            timestamp = TimeGenerator.currentTime()
        )
    }

    private suspend fun sendToBot(prompt: String){
        try {
            val replytext = "He recibido: $prompt"
            val botMsg: Menssage = prepareBotMessage(replytext)
            onEvent(HomeEvent.BotReplyArrived(botMsg))
        }catch (t: Throwable){
            onEvent(HomeEvent.BotReplyFailed("Error"))
        }
    }

    private fun mapErrorToText(throwable: Throwable) {

    }

    private fun loadHistory() {
    }

    private fun persistMessages(messages: List<Menssage>) {

    }
}

internal interface IdGenerator {
    fun nextId(): Long
}

//Con el object tenemos un solo generado, el que crea el id es el nextID
internal object AutoId : IdGenerator {
    private val counter = java.util.concurrent.atomic.AtomicLong(System.currentTimeMillis())
    override fun nextId(): Long = counter.incrementAndGet()
}

interface MessageTime {
    fun currentTime(): String
}

internal object TimeGenerator : MessageTime {
    override fun currentTime(): String {
        val now = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = now.format(formatter)
        return formattedTime
    }
}

internal interface GenerateBotReplySuspend {
    suspend fun generateBotReply(prompt: String): Result<Menssage>
}

interface HistoryRepository
