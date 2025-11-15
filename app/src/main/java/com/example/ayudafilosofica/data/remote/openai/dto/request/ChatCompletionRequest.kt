package com.example.ayudafilosofica.data.remote.openai.dto.request

data class ChatCompletionRequest(
    val model: String = "gpt-4.1-mini",
    val messages: List<ChatMessageDTO>,
    val temperature: Double? = 0.7
)
