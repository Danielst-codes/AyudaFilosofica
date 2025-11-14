package com.example.ayudafilosofica.data.repository

import com.example.ayudafilosofica.core.Menssage
import com.example.ayudafilosofica.data.remote.dto.request.ContentDTO
import com.example.ayudafilosofica.data.remote.dto.request.PartDTO
import com.example.ayudafilosofica.data.remote.dto.response.GenerateContentResponse

// Dominio -> API (para historial):
fun Menssage.toContentDTO(): ContentDTO =
    ContentDTO(
        role = if (deUsuario) "user" else "model",
        parts = listOf(PartDTO(text = texto))
    )

fun List<Menssage>.toTurns(): List<ContentDTO> = map { it.toContentDTO() }

// API -> Dominio (respuesta bot):
fun GenerateContentResponse.toBotMenssage(
    idFactory: () -> Long,
    timeFactory: () -> String
): Menssage {
    val text = candidates
        .firstOrNull()
        ?.content
        ?.parts
        ?.firstOrNull()
        ?.text
        ?: "sin texto"
    return Menssage(
        id = idFactory(),
        texto = text,
        deUsuario = false,
        timestamp = timeFactory()
    )
}
