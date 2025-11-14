package com.example.ayudafilosofica.data.remote.dto.request

import com.example.ayudafilosofica.data.remote.dto.response.GenerationConfigDTO
import com.google.ai.client.generativeai.type.Content
import com.google.gson.annotations.SerializedName

data class GenerateContentRequest(
    val contents: List<ContentDTO>,
    val systemInstruction: ContentDTO? = null,
    val generationConfig: GenerationConfigDTO? = null
)
