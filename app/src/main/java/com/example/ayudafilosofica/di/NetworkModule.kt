package com.example.ayudafilosofica.di


import com.example.ayudafilosofica.data.remote.dto.GeminiApi
import com.example.ayudafilosofica.data.remote.dto.RetrofitModule
import com.example.ayudafilosofica.data.repository.GeminiRepository
import com.example.ayudafilosofica.domain.CosasPhylosophy.SelectedPhilosophiesRepository
import com.example.ayudafilosofica.domain.cosasBot.GenerateBotReplySuspend
import com.example.ayudafilosofica.domain.cosasBot.GenerateBotReplySuspendImpl
import com.example.ayudafilosofica.domain.ids.IdGenerator
import com.example.ayudafilosofica.domain.ids.MessageTime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideRetrofitModule(): RetrofitModule = RetrofitModule()

    @Provides @Singleton
    fun provideGeminiApi(r: RetrofitModule): GeminiApi = r.api

    @Provides @Singleton
    fun provideGeminiRepository(
        r: RetrofitModule,
        api: GeminiApi
    ): GeminiRepository = GeminiRepository(api, r.geminiApiKey)

    @Provides @Singleton
    fun provideGenerateBotReplySuspend(
        repo: GeminiRepository,
        idGen: IdGenerator,
        clock: MessageTime,
        selected: SelectedPhilosophiesRepository
    ): GenerateBotReplySuspend =
        GenerateBotReplySuspendImpl(repo, idGen, clock, selected)
}
