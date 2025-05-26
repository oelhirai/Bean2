package com.example.bean2.data.local.converter

import androidx.room.TypeConverter
import com.example.bean2.data.model.CoffeeType

class CoffeeTypeConverter {
    @TypeConverter
    fun fromCoffeeType(type: CoffeeType): String {
        return type.name
    }

    @TypeConverter
    fun toCoffeeType(value: String): CoffeeType {
        return enumValueOf(value)
    }
}
