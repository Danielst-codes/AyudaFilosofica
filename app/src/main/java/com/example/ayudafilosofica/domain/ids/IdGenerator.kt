package com.example.ayudafilosofica.domain.ids

import java.util.concurrent.atomic.AtomicLong

interface IdGenerator {
    fun nextId(): Long
}

//Con el object tenemos un solo generado, el que crea el id es el nextID
object AutoId : IdGenerator {
    private val counter = AtomicLong(System.currentTimeMillis())
    override fun nextId(): Long = counter.incrementAndGet()
}

