package com.example.ayudafilosofica.feature.auth.presentation

import com.example.ayudafilosofica.domain.phylosophy.model.Philosophy


data class PhilosophyState(
    val items : List<Philosophy> = emptyList(),
    val selected : Set<String> = emptySet(),
    val isSaving : Boolean = false,
    val warnedAboutThree : Boolean = false )


sealed interface PhilosophyEvent{
    data class Toggle(val id: String) : PhilosophyEvent
    data object SaveClicked : PhilosophyEvent
    data object LoadRequested : PhilosophyEvent

}

sealed interface PhilosophyEffect {
    data class ShowSnackbar(val text: String) : PhilosophyEffect
    data object GoHome : PhilosophyEffect
}