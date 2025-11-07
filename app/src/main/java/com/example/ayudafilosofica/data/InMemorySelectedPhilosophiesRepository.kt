package com.example.ayudafilosofica.data

import com.example.ayudafilosofica.domain.CosasPhylosophy.SelectedPhilosophiesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class InMemorySelectedPhilosophiesRepository @Inject constructor() : SelectedPhilosophiesRepository {
    private val selected = MutableStateFlow(emptySet<String>())
    override val selectedIds = selected

    override suspend fun setSelected(ids: Set<String>) {
        selected.value = ids
    }

}