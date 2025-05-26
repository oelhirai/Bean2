package com.example.bean2.data.local.converter

import android.util.Log
import androidx.room.TypeConverter
import com.example.bean2.data.model.BrewingParams
import com.example.bean2.data.model.Dripper
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class BrewingParamsConverter {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun fromString(value: String?): BrewingParams? {
        if (value == null) return null
        
        return try {
            val type = Types.newParameterizedType(Map::class.java, String::class.java, Any::class.java)
            val adapter = moshi.adapter<Map<String, Any>>(type)
            val map = adapter.fromJson(value) ?: return null
            
            return when (map["type"] as? String) {
            "PourOverParams" -> BrewingParams.PourOverParams(
                temperature = (map["temperature"] as? Double)?.toInt() ?: 0,
                grindSize = map["grindSize"] as? String ?: "",
                ratio = map["ratio"] as? String ?: "",
                dripper = Dripper.valueOf(map["dripper"] as? String ?: Dripper.V60.name),
                notes = map["notes"] as? String ?: ""
            )
            "EspressoParams" -> BrewingParams.EspressoParams(
                temperature = (map["temperature"] as? Double)?.toInt() ?: 0,
                grindSize = map["grindSize"] as? String ?: "",
                ratio = map["ratio"] as? String ?: "",
                yield = map["yield"] as? String ?: "",
                extractionTime = (map["extractionTime"] as? Double)?.toInt() ?: 0,
                notes = map["notes"] as? String ?: ""
            )
                else -> {
                    android.util.Log.w("BrewingParams", "Unknown brewing params type: ${map["type"]}")
                    null
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("BrewingParams", "Error deserializing brewing params: ${e.message}")
            null
        }
    }

    @TypeConverter
    fun toString(brewingParams: BrewingParams?): String? {
        if (brewingParams == null) return null
        
        return try {
            val map = mutableMapOf<String, Any>(
                "temperature" to brewingParams.temperature,
                "grindSize" to brewingParams.grindSize,
                "ratio" to brewingParams.ratio,
                "notes" to brewingParams.notes,
                "type" to brewingParams.javaClass.simpleName
            )
            
            when (brewingParams) {
                is BrewingParams.PourOverParams -> {
                    map["dripper"] = brewingParams.dripper.name
                }
                is BrewingParams.EspressoParams -> {
                    map["yield"] = brewingParams.yield
                    map["extractionTime"] = brewingParams.extractionTime
                }
            }
            
            val type = Types.newParameterizedType(Map::class.java, String::class.java, Any::class.java)
            val adapter = moshi.adapter<Map<String, Any>>(type)
            adapter.toJson(map)
        } catch (e: Exception) {
            Log.e("BrewingParams", "Error serializing brewing params: ${e.message}")
            null
        }
    }
}
