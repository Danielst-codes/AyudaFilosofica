package com.example.ayudafilosofica.domain.cosasBot

import com.example.ayudafilosofica.core.Menssage
import com.example.ayudafilosofica.data.remote.dto.request.ContentDTO
import com.example.ayudafilosofica.data.remote.dto.request.PartDTO
import com.example.ayudafilosofica.data.repository.GeminiRepository
import com.example.ayudafilosofica.data.repository.toBotMenssage
import com.example.ayudafilosofica.data.repository.toTurns
import com.example.ayudafilosofica.domain.CosasPhylosophy.SelectedPhilosophiesRepository
import com.example.ayudafilosofica.domain.ids.IdGenerator
import com.example.ayudafilosofica.domain.ids.MessageTime
import kotlinx.coroutines.flow.firstOrNull  // <-- IMPORTANTE

class GenerateBotReplySuspendImpl(
    private val repo: GeminiRepository,
    private val idGen: IdGenerator,
    private val clock: MessageTime,
    private val selectedPhilosophies: SelectedPhilosophiesRepository
) : GenerateBotReplySuspend {

    override suspend fun generateBotReply(
        prompt: String,
        history: List<Menssage>
    ): Result<Menssage> = runCatching {
        // 1) turns (historial + último prompt)
        val turns = (history + Menssage(
            id = -1, texto = prompt, deUsuario = true, timestamp = ""
        )).toTurns()

        // 2) leer selección actual (Flow -> valor)
        val selected: Set<String> =
            selectedPhilosophies.selectedIds.firstOrNull() ?: emptySet()

        // 3) system_instruction opcional
        val system = if (selected.isNotEmpty()) {
            val joined = selected.joinToString(", ")
            ContentDTO(
                role = "user",
                parts = listOf(
                    PartDTO(
                        "Actúa como tutor que combina: $joined. " +
                                "Sé claro, riguroso y práctico."
                    )
                )
            )
        } else null

        // 4) llamada + mapeo a Menssage
        val resp = repo.chat(turns, system)
        resp.toBotMenssage(
            idFactory = { idGen.nextId() },
            timeFactory = { clock.currentTime() }
        )
    }
}
