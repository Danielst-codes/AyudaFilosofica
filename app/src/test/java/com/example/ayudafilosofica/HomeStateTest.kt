package com.example.ayudafilosofica

import com.example.ayudafilosofica.feature.home.presentation.HomeEvent
import com.example.ayudafilosofica.feature.home.presentation.HomeState
import com.example.ayudafilosofica.feature.home.presentation.reduce
import junit.framework.TestCase
import org.junit.Test

class HomeStateTest {

    @Test
    fun inputChanged_updatesInputText_andCanSend_whenNotSending() {
        // Estado inicial: no estamos enviando y no hay texto
        val initial = HomeState(
            messages = emptyList(),
            inputText = "",
            isSending = false,
            canSend = false
        )

        // Evento: el usuario escribe algo
        val event = HomeEvent.InputChanged("Hola filosofo")

        // Reducimos el estado con ese evento
        val next = initial.reduce(event)

        // Comprobamos que se haya guardado el texto
        TestCase.assertEquals("Hola filosofo", next.inputText)

        // Como hay texto y no estamos enviando, se debería poder enviar
        TestCase.assertTrue(next.canSend)
    }

    @Test
    fun inputChanged_doesNotEnableCanSend_whenIsSending() {
        // Estado inicial: ya estamos enviando (spinner, desactivar botón, etc.)
        val initial = HomeState(
            messages = emptyList(),
            inputText = "",
            isSending = true,
            canSend = false
        )

        // Evento: el usuario escribe mientras ya se está enviando
        val event = HomeEvent.InputChanged("Mensaje mientras espero al bot")

        // Reducimos el estado
        val next = initial.reduce(event)

        // El texto sí debe actualizarse
        TestCase.assertEquals("Mensaje mientras espero al bot", next.inputText)

        // Pero el botón NO debe habilitarse porque isSending = true
        TestCase.assertFalse(next.canSend)
    }
}