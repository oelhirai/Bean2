package com.example.bean2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bean2.data.model.BrewingParams
import com.example.bean2.data.model.CoffeeType
import java.util.UUID

@Entity(tableName = "coffee_bags")
data class CoffeeBagEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val roaster: String,
    val type: CoffeeType,
    val brewingParams: BrewingParams? = null,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
