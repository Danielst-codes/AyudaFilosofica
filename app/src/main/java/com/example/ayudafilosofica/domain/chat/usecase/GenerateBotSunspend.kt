package com.example.ayudafilosofica.domain.chat.usecase

import com.example.ayudafilosofica.core.model.Menssage

interface GenerateBotReplySuspend {
    suspend fun generateBotReply(
        prompt: String,
        history: List<Menssage> = emptyList()
    ): Result<Menssage>
}
