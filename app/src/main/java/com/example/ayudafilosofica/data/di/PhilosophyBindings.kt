package com.example.ayudafilosofica.data.di

import com.example.ayudafilosofica.data.repository.InMemorySelectedPhilosophiesRepository
import com.example.ayudafilosofica.data.repository.StaticPhilosophyRepository
import com.example.ayudafilosofica.domain.phylosophy.repository.PhilosophyRepository
import com.example.ayudafilosofica.domain.phylosophy.repository.SelectedPhilosophiesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PhilosophyBindings {
    @Binds
    abstract fun bindPhilosophyRepository(
        impl: StaticPhilosophyRepository
    ): PhilosophyRepository

    @Binds
    @Singleton
    abstract fun bindSelectedPhilosophiesRepository(
        impl: InMemorySelectedPhilosophiesRepository
    ): SelectedPhilosophiesRepository
}