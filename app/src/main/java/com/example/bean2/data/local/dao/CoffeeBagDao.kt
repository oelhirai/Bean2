package com.example.bean2.data.local.dao

import androidx.room.*
import com.example.bean2.data.local.entity.CoffeeBagEntity
import com.example.bean2.data.model.CoffeeType
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeBagDao {
    @Query("SELECT * FROM coffee_bags ORDER BY updatedAt DESC")
    fun getAllCoffeeBags(): Flow<List<CoffeeBagEntity>>
    
    @Query("SELECT * FROM coffee_bags WHERE type = :type ORDER BY updatedAt DESC")
    fun getCoffeeBagsByType(type: CoffeeType): Flow<List<CoffeeBagEntity>>
    
    @Query("SELECT * FROM coffee_bags WHERE id = :id")
    suspend fun getCoffeeBagById(id: String): CoffeeBagEntity?
    
    @Query("SELECT * FROM coffee_bags WHERE id = :id")
    fun getCoffeeBagByIdFlow(id: String): Flow<CoffeeBagEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeBag(coffeeBag: CoffeeBagEntity)
    
    @Query("UPDATE coffee_bags SET name = :name, roaster = :roaster, type = :type, brewingParams = :brewingParams, notes = :notes, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateCoffeeBag(
        id: String,
        name: String,
        roaster: String,
        type: CoffeeType,
        brewingParams: String?,
        notes: String,
        updatedAt: Long
    )
    
    @Update
    suspend fun updateCoffeeBag(coffeeBag: CoffeeBagEntity)
    
    @Delete
    suspend fun deleteCoffeeBag(coffeeBag: CoffeeBagEntity)
}
