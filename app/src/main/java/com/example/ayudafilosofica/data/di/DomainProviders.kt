package com.example.ayudafilosofica.data.di

import com.example.ayudafilosofica.domain.util.AutoId
import com.example.ayudafilosofica.domain.util.IdGenerator
import com.example.ayudafilosofica.domain.util.MessageTime
import com.example.ayudafilosofica.domain.util.TimeGenerator
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