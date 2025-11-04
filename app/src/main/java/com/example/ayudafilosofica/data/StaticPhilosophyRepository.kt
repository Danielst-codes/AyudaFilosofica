package com.example.ayudafilosofica.data

import com.example.ayudafilosofica.domain.Philosophy
import com.example.ayudafilosofica.domain.PhilosophyRepository
import javax.inject.Inject

class StaticPhilosophyRepository @Inject constructor() : PhilosophyRepository {
    private val items = listOf(
        Philosophy("stoicism", "Estoicismo", false),
        Philosophy("existentialism", "Existencialismo", false),
        Philosophy("aristotelian", "Aristotelismo", false),
        Philosophy("platonism", "Platonismo", false),
        Philosophy("nihilism", "Nihilismo", false),
        Philosophy("utilitarianism", "Utilitarismo", false),
        Philosophy("kantian", "Kantianismo", false)

    )

    override fun getAll(): List<Philosophy> = items
}