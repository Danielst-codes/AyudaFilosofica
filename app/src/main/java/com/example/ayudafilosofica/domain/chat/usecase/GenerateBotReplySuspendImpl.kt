package com.example.ayudafilosofica.domain.chat.usecase

import com.example.ayudafilosofica.core.model.Menssage
import com.example.ayudafilosofica.data.remote.openai.mappers.toBotMenssage
import com.example.ayudafilosofica.data.repository.OpenAIRepository
import com.example.ayudafilosofica.domain.phylosophy.repository.SelectedPhilosophiesRepository
import com.example.ayudafilosofica.domain.util.IdGenerator
import com.example.ayudafilosofica.domain.util.MessageTime
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GenerateBotReplySuspendImpl @Inject constructor(
    private val repo: OpenAIRepository,
    private val idGen: IdGenerator,
    private val clock: MessageTime,
    private val selectedPhilosophies: SelectedPhilosophiesRepository
) : GenerateBotReplySuspend {

    override suspend fun generateBotReply(
        prompt: String,
        history: List<Menssage>
    ): Result<Menssage> = runCatching {
        // 1) leer selección actual (Flow -> valor)
        val selected: Set<String> =
            selectedPhilosophies.selectedIds.firstOrNull() ?: emptySet()

        // 2) construir systemPrompt (antes era un ContentDTO)
        val systemPrompt: String? = if (selected.isNotEmpty()) {
            val joined = selected.joinToString(", ")
            """
            Actúa como un tutor filosófico que combina: $joined.
            Tu objetivo es ayudar a la persona a reflexionar y tomar decisiones con claridad.
            Sé claro, riguroso y práctico. Responde SIEMPRE en español.
            Resume cuando sea posible y da ejemplos concretos aplicables al día a día.
            Mantén un tono empático y respetuoso. Evita jerga demasiado técnica.
            No des consejos médicos ni psicológicos profesionales.
            """.trimIndent()
        } else {
            """
                Actúa como un tutor filosófico general.
                Sé claro, riguroso y práctico. Responde SIEMPRE en español.
                Resume cuando sea posible y da ejemplos concretos aplicables al día a día.
                Mantén un tono empático y respetuoso.
                """.trimIndent()
        }

        // 3) llamada al repositorio (historial completo + prompt ya está en history)
        val resp = repo.chat(
            turns = history,
            systemPrompt = systemPrompt
        )

        // 4) mapear respuesta de OpenAI -> Menssage del bot
        resp.toBotMenssage(
            idFactory = { idGen.nextId() },
            timeFactory = { clock.currentTime() }
        )
    }
}