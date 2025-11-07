package com.example.ayudafilosofica.di

import com.example.ayudafilosofica.domain.ids.AutoId
import com.example.ayudafilosofica.domain.ids.IdGenerator
import com.example.ayudafilosofica.domain.ids.MessageTime
import com.example.ayudafilosofica.domain.ids.TimeGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainProviders {

    @Provides
    fun prvideIdGenerator() : IdGenerator = AutoId

    @Provides
    fun provideMenssageTime() : MessageTime = TimeGenerator
}

    //@Provides
    //fun provideGenerateBotReply(): GenerateBotReplySuspend = GenerateBotReplyFake