package com.example.ayudafilosofica.data.repository

import com.example.ayudafilosofica.core.model.Menssage
import com.example.ayudafilosofica.data.remote.openai.api.OpenAIApi
import com.example.ayudafilosofica.data.remote.openai.dto.request.ChatCompletionRequest
import com.example.ayudafilosofica.data.remote.openai.dto.request.ChatMessageDTO
import com.example.ayudafilosofica.data.remote.openai.dto.response.ChatCompletionResponse
import com.example.ayudafilosofica.data.remote.openai.mappers.toChatMessageDTO

class OpenAIRepository(
    private val api: OpenAIApi
) {

    // Pregunta suelta sin historial
    suspend fun ask(prompt: String): ChatCompletionResponse =
        api.chatCompletion(
            ChatCompletionRequest(
                messages = listOf(
                    ChatMessageDTO(
                        role = "user",
                        content = prompt
                    )
                )
            )
        )

    // Chat con historial
    suspend fun chat(
        turns: List<Menssage>,
        systemPrompt: String? = null
    ): ChatCompletionResponse {
        val messages = mutableListOf<ChatMessageDTO>()

        systemPrompt?.let {
            messages += ChatMessageDTO(
                role = "system",
                content = it
            )
        }

        messages += turns.map { it.toChatMessageDTO() }

        return api.chatCompletion(
            ChatCompletionRequest(
                messages = messages
            )
        )
    }
}
