package com.example.ayudafilosofica.domain.cosasBot

import com.example.ayudafilosofica.core.Menssage

interface GenerateBotReplySuspend {
    suspend fun generateBotReply(prompt: String): Result<Menssage>

}
