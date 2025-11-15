package com.example.ayudafilosofica.feature.auth.presentation

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class PhilosophyStateTest {

    @Test
    fun toggle_agregaId_cuandoNoEstaSeleccionado() {
        val initial = PhilosophyState(
            items = emptyList(),
            selected = emptySet(),
            isSaving = false,
            warnedAboutThree = false
        )

        val event = PhilosophyEvent.Toggle("stoicism")
        val next = initial.reduce(event)

        assertTrue("stoicism" in next.selected)
        assertEquals(1, next.selected.size)
    }

    @Test
    fun toggle_eliminaId_cuandoYaEstabaSeleccionado(){
        val initial = PhilosophyState(
            items = emptyList(),
            selected = setOf("stoicism"),
            isSaving = false,
            warnedAboutThree = false
        )

        val event = PhilosophyEvent.Toggle("stoicism")
        val next = initial.reduce(event)

        assertFalse("stoicism" in next.selected)
        assertEquals(0, next.selected.size)
    }
}
