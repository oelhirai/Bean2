package com.example.bean2.data.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.bean2.data.local.dao.CoffeeBagDao
import com.example.bean2.data.local.entity.CoffeeBagEntity
import com.example.bean2.data.model.CoffeeType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class CoffeeBagDaoTest {

    private lateinit var coffeeBagDao: CoffeeBagDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        coffeeBagDao = db.coffeeBagDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetCoffeeBag() = runTest {
        val coffeeBag = CoffeeBagEntity(
            id = "1",
            name = "Test Coffee",
            roaster = "Test Roaster",
            type = CoffeeType.POUROVER
        )
        coffeeBagDao.insertCoffeeBag(coffeeBag)

        val byId = coffeeBagDao.getCoffeeBagById("1")
        assert(byId != null)
        assert(byId?.name == "Test Coffee")
    }

    @Test
    fun getAllCoffeeBags() = runTest {
        val coffeeBag1 = CoffeeBagEntity(
            id = "1",
            name = "Test Coffee 1",
            roaster = "Test Roaster",
            type = CoffeeType.POUROVER
        )
        val coffeeBag2 = CoffeeBagEntity(
            id = "2",
            name = "Test Coffee 2",
            roaster = "Test Roaster",
            type = CoffeeType.ESPRESSO
        )

        coffeeBagDao.insertCoffeeBag(coffeeBag1)
        coffeeBagDao.insertCoffeeBag(coffeeBag2)

        val allCoffeeBags = coffeeBagDao.getAllCoffeeBags().first()
        assert(allCoffeeBags.size == 2)
    }

    @Test
    fun getCoffeeBagsByType() = runTest {
        val coffeeBag1 = CoffeeBagEntity(
            id = "1",
            name = "Test Coffee 1",
            roaster = "Test Roaster",
            type = CoffeeType.POUROVER
        )
        val coffeeBag2 = CoffeeBagEntity(
            id = "2",
            name = "Test Coffee 2",
            roaster = "Test Roaster",
            type = CoffeeType.ESPRESSO
        )

        coffeeBagDao.insertCoffeeBag(coffeeBag1)
        coffeeBagDao.insertCoffeeBag(coffeeBag2)

        val pourOverBags = coffeeBagDao.getCoffeeBagsByType(CoffeeType.POUROVER).first()
        assert(pourOverBags.size == 1)
        assert(pourOverBags[0].type == CoffeeType.POUROVER)
    }

    @Test
    fun updateCoffeeBag() = runTest {
        val coffeeBag = CoffeeBagEntity(
            id = "1",
            name = "Test Coffee",
            roaster = "Test Roaster",
            type = CoffeeType.POUROVER
        )
        coffeeBagDao.insertCoffeeBag(coffeeBag)

        val updatedBag = coffeeBag.copy(name = "Updated Name")
        coffeeBagDao.updateCoffeeBag(updatedBag)

        val byId = coffeeBagDao.getCoffeeBagById("1")
        assert(byId?.name == "Updated Name")
    }

    @Test
    fun deleteCoffeeBag() = runTest {
        val coffeeBag = CoffeeBagEntity(
            id = "1",
            name = "Test Coffee",
            roaster = "Test Roaster",
            type = CoffeeType.POUROVER
        )
        coffeeBagDao.insertCoffeeBag(coffeeBag)

        coffeeBagDao.deleteCoffeeBag(coffeeBag)

        val byId = coffeeBagDao.getCoffeeBagById("1")
        assert(byId == null)
    }
}
