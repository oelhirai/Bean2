package com.example.bean2.data.mapper

import com.example.bean2.data.local.entity.CoffeeBagEntity
import com.example.bean2.data.model.BrewingParams
import com.example.bean2.data.model.CoffeeBag
import com.example.bean2.data.model.CoffeeType

fun CoffeeBagEntity.toCoffeeBag(): CoffeeBag {
    return CoffeeBag(
        id = id,
        name = name,
        roaster = roaster,
        type = type,
        brewingParams = brewingParams,
        notes = notes,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun CoffeeBag.toCoffeeBagEntity(): CoffeeBagEntity {
    return CoffeeBagEntity(
        id = id,
        name = name,
        roaster = roaster,
        type = type,
        brewingParams = brewingParams,
        notes = notes,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

// Extension functions for the mapper
fun CoffeeBag.withBrewingParams(params: BrewingParams): CoffeeBag {
    return this.copy(
        brewingParams = params,
        type = when (params) {
            is BrewingParams.PourOverParams -> CoffeeType.POUROVER
            is BrewingParams.EspressoParams -> CoffeeType.ESPRESSO
        }
    )
}
