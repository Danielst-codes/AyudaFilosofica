package com.example.ayudafilosofica.feature.auth.presentation

import com.example.ayudafilosofica.domain.phylosophy.model.Philosophy
import com.example.ayudafilosofica.domain.phylosophy.repository.PhilosophyRepository
import com.example.ayudafilosofica.domain.phylosophy.repository.SelectedPhilosophiesRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Test

// Fakes para el test

private class FakePhilosophyRepository : PhilosophyRepository {
    override fun getAll(): List<Philosophy> =
        listOf(
            Philosophy("stoicism", "Estoicismo"),
            Philosophy("existentialism", "Existencialismo")
        )
}

private class FakeSelectedRepo : SelectedPhilosophiesRepository {
    private val _ids = MutableStateFlow(emptySet<String>())
    override val selectedIds = _ids.asStateFlow()

    override suspend fun setSelected(ids: Set<String>) {
        _ids.value = ids
    }
}

// Test del ViewModel

class PhilosophyViewModelTest {

    @Test
    fun init_loadsPhilosophyList_intoState() {
        // 1. Crear el ViewModel con fakes
        val vm = PhilosophyViewModel(
            philosophies = FakePhilosophyRepository(),
            selectedRepo = FakeSelectedRepo()
        )

        // 2. Nada más crearse, en init llama a LoadRequested y rellena items
        val state = vm.state.value

        // 3. Comprobamos que se ha cargado la lista del repositorio
        assertEquals(2, state.items.size)
        assertEquals("stoicism", state.items[0].id)
        assertEquals("existentialism", state.items[1].id)
    }
}
