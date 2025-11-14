package com.example.ayudafilosofica.domain.phylosophy.repository

import kotlinx.coroutines.flow.Flow

interface SelectedPhilosophiesRepository {
    val selectedIds : Flow<Set<String>>
    suspend fun setSelected(ids: Set<String>)
}