package com.example.ayudafilosofica.data.di

import com.example.ayudafilosofica.data.remote.RetrofitModule
import com.example.ayudafilosofica.data.remote.openai.api.OpenAIApi
import com.example.ayudafilosofica.data.repository.OpenAIRepository
import com.example.ayudafilosofica.domain.phylosophy.repository.SelectedPhilosophiesRepository
import com.example.ayudafilosofica.domain.chat.usecase.GenerateBotReplySuspend
import com.example.ayudafilosofica.domain.chat.usecase.GenerateBotReplySuspendImpl
import com.example.ayudafilosofica.domain.util.IdGenerator
import com.example.ayudafilosofica.domain.util.MessageTime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitModule(): RetrofitModule = RetrofitModule()

    @Provides
    @Singleton
    fun provideOpenAIApi(r: RetrofitModule): OpenAIApi = r.api

    @Provides
    @Singleton
    fun provideOpenAIRepository(
        api: OpenAIApi
    ): OpenAIRepository = OpenAIRepository(api)

    @Provides
    @Singleton
    fun provideGenerateBotReplySuspend(
        repo: OpenAIRepository,
        idGen: IdGenerator,
        clock: MessageTime,
        selected: SelectedPhilosophiesRepository
    ): GenerateBotReplySuspend =
        GenerateBotReplySuspendImpl(repo, idGen, clock, selected)
}
