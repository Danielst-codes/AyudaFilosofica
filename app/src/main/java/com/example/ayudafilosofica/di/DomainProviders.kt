package com.example.ayudafilosofica.di

import com.example.ayudafilosofica.domain.AutoId
import com.example.ayudafilosofica.domain.IdGenerator
import com.example.ayudafilosofica.domain.MessageTime
import com.example.ayudafilosofica.domain.TimeGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.ayudafilosofica.domain.GenerateBotReplySuspend

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