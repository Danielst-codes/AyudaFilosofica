package com.example.ayudafilosofica.domain

import com.example.ayudafilosofica.core.Menssage

interface GenerateBotReplySuspend {
    suspend fun generateBotReply(prompt: String): Result<Menssage>

}
