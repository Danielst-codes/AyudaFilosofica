package com.example.ayudafilosofica.data.remote.openai.dto.request

data class ChatMessageDTO(
    val role: String,   // "system", "user"
    val content: String
)
