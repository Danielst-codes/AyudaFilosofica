package com.example.ayudafilosofica.di

import com.example.ayudafilosofica.data.BotResopitoryImpl
import com.example.ayudafilosofica.data.InMemorySelectedPhilosophiesRepository
import com.example.ayudafilosofica.data.StaticPhilosophyRepository
import com.example.ayudafilosofica.domain.CosasPhylosophy.Philosophy
import com.example.ayudafilosofica.domain.CosasPhylosophy.PhilosophyRepository
import com.example.ayudafilosofica.domain.CosasPhylosophy.SelectedPhilosophiesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PhilosophyBindings {
    @Binds
    abstract fun bindPhilosophyRepository(
        impl: StaticPhilosophyRepository
    ): PhilosophyRepository

    @Binds
    abstract fun bindSelectedPhilosophiesRepository(
        impl: InMemorySelectedPhilosophiesRepository
    ): SelectedPhilosophiesRepository
}