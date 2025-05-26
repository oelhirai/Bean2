package com.example.bean2.data.repository

import com.example.bean2.data.local.dao.CoffeeBagDao
import com.example.bean2.data.mapper.toCoffeeBag
import com.example.bean2.data.mapper.toCoffeeBagEntity
import com.example.bean2.data.model.CoffeeBag
import com.example.bean2.data.model.CoffeeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface CoffeeRepository {
    fun getAllCoffeeBags(): Flow<List<CoffeeBag>>
    fun getCoffeeBagsByType(type: CoffeeType?): Flow<List<CoffeeBag>>
    suspend fun getCoffeeBagById(id: String): CoffeeBag?

    fun getCoffeeBagByIdFlow(id: String): Flow<CoffeeBag?>
    suspend fun addCoffeeBag(coffeeBag: CoffeeBag)
    suspend fun updateCoffeeBag(updatedBag: CoffeeBag)
    suspend fun deleteCoffeeBag(id: String)
}

@Singleton
class CoffeeRepositoryImpl @Inject constructor(
    private val coffeeBagDao: CoffeeBagDao
) : CoffeeRepository {

    override fun getAllCoffeeBags(): Flow<List<CoffeeBag>> {
        return coffeeBagDao.getAllCoffeeBags()
            .map { entities -> entities.map { it.toCoffeeBag() } }
    }

    override fun getCoffeeBagsByType(type: CoffeeType?): Flow<List<CoffeeBag>> {
        return if (type == null) {
            getAllCoffeeBags()
        } else {
            coffeeBagDao.getCoffeeBagsByType(type)
                .map { entities -> entities.map { it.toCoffeeBag() } }
        }
    }

    override suspend fun getCoffeeBagById(id: String): CoffeeBag? {
        return coffeeBagDao.getCoffeeBagById(id)?.toCoffeeBag()
    }

    override fun getCoffeeBagByIdFlow(id: String): Flow<CoffeeBag?> {
        return coffeeBagDao.getCoffeeBagByIdFlow(id)
            .map { it?.toCoffeeBag() }
    }

    override suspend fun addCoffeeBag(coffeeBag: CoffeeBag) {
        coffeeBagDao.insertCoffeeBag(
            coffeeBag.toCoffeeBagEntity()
        )
    }

    override suspend fun updateCoffeeBag(updatedBag: CoffeeBag) {
        coffeeBagDao.updateCoffeeBag(
            updatedBag.toCoffeeBagEntity()
        )
    }

    override suspend fun deleteCoffeeBag(id: String) {
        getCoffeeBagById(id)?.let { coffeeBag ->
            coffeeBagDao.deleteCoffeeBag(coffeeBag.toCoffeeBagEntity())
        }
    }
}
