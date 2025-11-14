package com.example.ayudafilosofica.data.remote.openai.api

import com.example.ayudafilosofica.data.remote.openai.dto.request.ChatCompletionRequest
import com.example.ayudafilosofica.data.remote.openai.dto.response.ChatCompletionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAIApi {

    @POST("v1/chat/completions")
    suspend fun chatCompletion(
        @Body body: ChatCompletionRequest
    ): ChatCompletionResponse
}