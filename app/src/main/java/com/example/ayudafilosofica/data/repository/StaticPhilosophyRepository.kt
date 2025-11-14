package com.example.ayudafilosofica.data.repository

import com.example.ayudafilosofica.domain.phylosophy.model.Philosophy
import com.example.ayudafilosofica.domain.phylosophy.repository.PhilosophyRepository
import javax.inject.Inject

class StaticPhilosophyRepository @Inject constructor() : PhilosophyRepository {
    private val items = listOf(
        Philosophy("stoicism", "Estoicismo"),
        Philosophy("existentialism", "Existencialismo"),
        Philosophy("aristotelian", "Aristotelismo"),
        Philosophy("platonism", "Platonismo"),
        Philosophy("nihilism", "Nihilismo"),
        Philosophy("utilitarianism", "Utilitarismo"),
        Philosophy("kantian", "Kantianismo")

    )

    override fun getAll(): List<Philosophy> = items
}