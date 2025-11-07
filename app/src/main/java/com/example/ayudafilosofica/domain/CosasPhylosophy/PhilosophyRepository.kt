package com.example.ayudafilosofica.domain.CosasPhylosophy

interface PhilosophyRepository {
    fun getAll(): List<Philosophy>
}