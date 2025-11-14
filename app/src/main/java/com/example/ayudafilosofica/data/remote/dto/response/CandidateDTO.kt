package com.example.ayudafilosofica.data.remote.dto.response
import com. example. ayudafilosofica. data. remote. dto. request. ContentDTO
import com.google.ai.client.generativeai.type.FinishReason

data class CandidateDTO(
    val content : ContentDTO?,
    val finishReason: String?
    )