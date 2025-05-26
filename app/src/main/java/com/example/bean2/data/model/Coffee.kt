package com.example.bean2.data.model

import java.util.UUID

data class CoffeeBag(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val roaster: String,
    val type: CoffeeType,
    val brewingParams: BrewingParams? = null,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    fun withUpdatedTimestamp(): CoffeeBag {
        return this.copy(updatedAt = System.currentTimeMillis())
    }
}

enum class CoffeeType {
    POUROVER, ESPRESSO
}

sealed class BrewingParams {
    abstract val temperature: Int
    abstract val grindSize: String
    abstract val ratio: String
    abstract val notes: String

    data class PourOverParams(
        override val temperature: Int,
        override val grindSize: String,
        override val ratio: String,
        val dripper: Dripper = Dripper.V60,
        override val notes: String = ""
    ) : BrewingParams()

    data class EspressoParams(
        override val temperature: Int,
        override val grindSize: String,
        override val ratio: String,
        val yield: String,
        val extractionTime: Int, // in seconds
        override val notes: String = ""
    ) : BrewingParams()
}

enum class Dripper {
    V60, PULSAR, KALITA_WAVE, OTHER
}
