package com.example.ayudafilosofica.data.remote.dto.response

data class GenerationConfigDTO(
    val temperature: Double? = null,
    val topK: Int? = null,
    val topP: Double? = null,
    val maxOutputTokens: Int? = null
)
