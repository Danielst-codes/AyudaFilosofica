package com.example.ayudafilosofica.feature.auth.presentation

import com.example.ayudafilosofica.domain.Philosophy
import com.example.ayudafilosofica.domain.PhilosophyRepository


data class PhilosophyState(
    val listPhilosophy : List<Philosophy>,
    val selected : Set<String>,
    val isSaving : Boolean = false,
    val onSave : Boolean = false ){
}

sealed interface PhilosphyEvent{
    data class Toggle(val id: String) : PhilosphyEvent

}