package com.example.ayudafilosofica.feature.auth.presentation

fun PhilosophyState.reduce(event: PhilosophyEvent) : PhilosophyState{
    return when(event) {
        PhilosophyEvent.LoadRequested -> this
        PhilosophyEvent.SaveClicked -> copy(isSaving = true)
        is PhilosophyEvent.Toggle -> {
            val nextSelected =
                if (event.id in selected) selected - event.id else selected + event.id
            copy(selected = nextSelected)
        }
    }
}