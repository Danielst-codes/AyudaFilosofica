package com.example.ayudafilosofica.data.remote.dto

import com.example.ayudafilosofica.data.remote.dto.request.GenerateContentRequest
import com.example.ayudafilosofica.data.remote.dto.response.GenerateContentResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApi {
    @POST("v1beta/models/gemini-2.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey : String,
        @Body body: GenerateContentRequest
    ) : GenerateContentResponse

}