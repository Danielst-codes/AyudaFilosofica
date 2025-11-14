package com.example.ayudafilosofica.data.remote.openai.mappers

import com.example.ayudafilosofica.core.model.Menssage
import com.example.ayudafilosofica.data.remote.openai.dto.request.ChatMessageDTO
import com.example.ayudafilosofica.data.remote.openai.dto.response.ChatCompletionResponse

// Dominio -> API
fun Menssage.toChatMessageDTO(): ChatMessageDTO =
    ChatMessageDTO(
        role = if (deUsuario) "user" else "assistant",
        content = texto
    )

fun List<Menssage>.toChatMessages(): List<ChatMessageDTO> = map { it.toChatMessageDTO() }

// API -> Dominio (respuesta bot)
fun ChatCompletionResponse.toBotMenssage(
    idFactory: () -> Long,
    timeFactory: () -> String
): Menssage {
    val text = choices
        .firstOrNull()
        ?.message
        ?.content
        ?: "sin texto"

    return Menssage(
        id = idFactory(),
        texto = text,
        deUsuario = false,
        timestamp = timeFactory()
    )
}
