package com.example.ayudafilosofica.domain.phylosophy.repository

import com.example.ayudafilosofica.domain.phylosophy.model.Philosophy

interface PhilosophyRepository {
    fun getAll(): List<Philosophy>
}