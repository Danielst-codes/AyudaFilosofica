package com.example.ayudafilosofica.data.remote.openai.dto.response

import com.example.ayudafilosofica.data.remote.openai.dto.request.ChatMessageDTO

data class ChatCompletionResponse(
    val id: String,
    val choices: List<Choice>,
    val created: Long,
    val model: String
){
    data class Choice(
        val index: Int,
        val message: ChatMessageDTO,
        val finish_reason: String?
    )
}
