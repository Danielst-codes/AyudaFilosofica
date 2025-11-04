package com.example.ayudafilosofica.domain

interface PhilosophyRepository {
    fun getAll(): List<Philosophy>
}