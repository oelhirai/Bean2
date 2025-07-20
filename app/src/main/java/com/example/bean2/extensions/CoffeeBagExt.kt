package com.example.bean2.extensions

import com.example.bean2.data.model.CoffeeBag
import com.example.bean2.data.model.CoffeeType

fun CoffeeBag?.getTemperature(): String =
    this?.brewingParams?.temperature?.toString() ?: ""

fun CoffeeBag?.getGrind(): String =
    this?.brewingParams?.grindSize?.toString() ?: ""

fun CoffeeBag?.getRatio(): String =
    this?.brewingParams?.ratio?.toString() ?: ""
