package com.example.ayudafilosofica.domain.CosasPhylosophy

import kotlinx.coroutines.flow.Flow

interface SelectedPhilosophiesRepository {
    val selectedIds : Flow<Set<String>>
    suspend fun setSelected(ids: Set<String>)
}