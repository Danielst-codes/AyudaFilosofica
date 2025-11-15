package com.example.ayudafilosofica.feature.home.presentation

import com.example.ayudafilosofica.MainDispatcherRule
import com.example.ayudafilosofica.core.model.Menssage
import com.example.ayudafilosofica.domain.chat.usecase.GenerateBotReplySuspend
import com.example.ayudafilosofica.domain.util.IdGenerator
import com.example.ayudafilosofica.domain.util.MessageTime
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

// ---------- Fakes ----------

private class FakeIdGenerator : IdGenerator {
    private var next = 1L
    override fun nextId(): Long = next++
}

private class FakeTime : MessageTime {
    override fun currentTime(): String = "12:00"
}

private class FakeBot : GenerateBotReplySuspend {
    override suspend fun generateBotReply(
        prompt: String,
        history: List<Menssage>
    ): Result<Menssage> {

        return Result.success(
            Menssage(
                id = 999,
                texto = "Respuesta del bot a: $prompt",
                deUsuario = false,
                timestamp = "12:00"
            )
        )
    }
}

// ---------- Test ----------

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun sendMessage_addsUserMessage_andBotMessage() = runTest {
        val vm = HomeViewModel(
            idGen = FakeIdGenerator(),
            clock = FakeTime(),
            bot = FakeBot()
        )

        // 1. Usuario escribe texto
        vm.onEvent(HomeEvent.InputChanged("Hola bot"))

        // 2. Usuario pulsa enviar
        vm.onEvent(HomeEvent.SendClicked)

        // Dejamos avanzar todas las coroutines pendientes (incluida la llamada al bot)
        advanceUntilIdle()

        val state = vm.state.value

        // ---- Comprobaciones ----

        // Deben existir 2 mensajes: usuario + bot
        assertEquals(2, state.messages.size)

        val userMsg = state.messages[0]
        val botMsg = state.messages[1]

        // Mensaje del usuario
        assertEquals("Hola bot", userMsg.texto)
        assertTrue(userMsg.deUsuario)

        // Mensaje del bot
        assertEquals("Respuesta del bot a: Hola bot", botMsg.texto)
        assertTrue(!botMsg.deUsuario)
    }
}
