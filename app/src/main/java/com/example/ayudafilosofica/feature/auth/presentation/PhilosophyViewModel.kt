package com.example.ayudafilosofica.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ayudafilosofica.domain.CosasPhylosophy.PhilosophyRepository
import com.example.ayudafilosofica.domain.CosasPhylosophy.SelectedPhilosophiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhilosophyViewModel @Inject constructor(
    private val philosophies: PhilosophyRepository,
    private val selectedRepo: SelectedPhilosophiesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(PhilosophyState())
    val state: StateFlow<PhilosophyState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<PhilosophyEffect>(replay = 0, extraBufferCapacity = 1)
    val effects = _effects.asSharedFlow()

    init {
        // 🚀 Al crear el VM, cargamos el catálogo
        onEvent(PhilosophyEvent.LoadRequested)
    }

    fun onEvent(event: PhilosophyEvent) {
        when (event) {

            // ✅ Carga inicial del catálogo
            PhilosophyEvent.LoadRequested -> {
                val items = philosophies.getAll()
                _state.update { it.copy(items = items) }
            }


            // ✅ Guardar selección
            PhilosophyEvent.SaveClicked -> {
                val s = state.value
                if (s.isSaving) return
                if (s.selected.isEmpty()) {
                    tryEmitEffect(PhilosophyEffect.ShowSnackbar("Elige al menos una filosofía."))
                    return
                }

                _state.update { it.reduce(PhilosophyEvent.SaveClicked) } // isSaving = true
                val ids = s.selected.toSet()

                viewModelScope.launch {
                    runCatching { selectedRepo.setSelected(ids) }
                        .onSuccess { emitEffect(PhilosophyEffect.GoHome) }
                        .onFailure {
                            _state.update { it.copy(isSaving = false) }
                            emitEffect(PhilosophyEffect.ShowSnackbar("No se pudo guardar. Inténtalo de nuevo."))
                        }
                }
            }

            is PhilosophyEvent.Toggle -> {
                val s = state.value
                val id = event.id

                if (id in s.selected) {
                    // Quitar siempre permitido
                    _state.update { it.reduce(event) }
                    return
                }

                if (s.selected.size == 3) {
                    emitEffect(PhilosophyEffect.ShowSnackbar("Máximo 3 filosofías."))
                    return
                }

                // Verificamos si debemos mostrar aviso al pasar a 2 o más
                val showInfo = !s.warnedAboutThree && (s.selected.size + 1) >= 2

                _state.update { it.reduce(event) } // Añadir selección

                if (showInfo) {
                    _state.update { it.copy(warnedAboutThree = true) }
                    tryEmitEffect(PhilosophyEffect.ShowSnackbar("Combinar varias filosofías puede diluir el consejo."))
                }
            }
        }
    }

    // 🔹 Efectos críticos (garantiza entrega)
    private fun emitEffect(effect: PhilosophyEffect) {
        viewModelScope.launch { _effects.emit(effect) }
    }

    // 🔹 Efectos no críticos (pueden perderse si el buffer está lleno)
    private fun tryEmitEffect(effect: PhilosophyEffect) {
        _effects.tryEmit(effect)
    }
}
