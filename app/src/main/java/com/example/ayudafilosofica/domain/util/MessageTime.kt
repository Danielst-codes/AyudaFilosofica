package com.example.ayudafilosofica.domain.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter

interface MessageTime {
    fun currentTime(): String
}

object TimeGenerator : MessageTime {
    override fun currentTime(): String {
        val now = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = now.format(formatter)
        return formattedTime
    }
}