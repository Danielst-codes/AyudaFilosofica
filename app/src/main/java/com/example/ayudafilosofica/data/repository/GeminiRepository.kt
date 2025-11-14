package com.example.ayudafilosofica.data.repository


import com.example.ayudafilosofica.data.remote.dto.GeminiApi
import com.example.ayudafilosofica.data.remote.dto.request.ContentDTO
import com.example.ayudafilosofica.data.remote.dto.request.GenerateContentRequest
import com.example.ayudafilosofica.data.remote.dto.request.PartDTO
import com.example.ayudafilosofica.data.remote.dto.request.ContentDTO as ContentIn

class GeminiRepository(
    private val api: GeminiApi,
    private val apiKey: String
) {

    suspend fun ask(prompt: String) =
        api.generateContent(
            apiKey,
            GenerateContentRequest(
                contents = listOf(ContentIn("user", listOf(PartDTO(prompt))))
            )
        )


    suspend fun chat(
        turns: List<ContentDTO>,
        systemInstruction: ContentDTO? = null
    ) = api.generateContent(
        apiKey,
        GenerateContentRequest(
            contents = turns,
            systemInstruction = systemInstruction
        )
    )
}
