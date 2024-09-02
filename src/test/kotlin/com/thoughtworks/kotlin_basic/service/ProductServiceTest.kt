package com.thoughtworks.kotlin_basic.service

import com.thoughtworks.kotlin_basic.model.Inventory
import com.thoughtworks.kotlin_basic.model.Product
import com.thoughtworks.kotlin_basic.model.ProductType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class ProductServiceTest {
    private lateinit var apiService: ApiService
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        apiService = mockk()
        productService = ProductService(apiService)
    }

    @Test
    fun `should get all products when call external service`(): Unit = runBlocking {
        val mockProducts = listOf(PRODUCT_1, PRODUCT_2)
        val mockInventories = listOf(INVENTORY_1, INVENTORY_2, INVENTORY_3)

        coEvery { apiService.getAllProducts().execute() } returns Response.success(mockProducts)
        coEvery { apiService.getAllInventories().execute() } returns Response.success(mockInventories)

        val result = productService.getAllProductWithInventory()
        assertEquals(2, result.size)
    }

    companion object {
        private val PRODUCT_1 = Product(id = 1, name = "Product 1", SKU = "SKU1", price = 10F, image = "image1", type = ProductType.NORMAL)
        private val PRODUCT_2 = Product(id = 2, name = "Product 2", SKU = "SKU2", price = 20F, image = "image2", type = ProductType.HIGH_DEMAND)

        private val INVENTORY_1 = Inventory(id = 123, SKU = "SKU1", quantity = 100, zone = "zone1")
        private val INVENTORY_2 = Inventory(id = 456, SKU = "SKU2", quantity = 50, zone = "zone2")
        private val INVENTORY_3 = Inventory(id = 789, SKU = "SKU2", quantity = 70, zone = "zone3")
    }
}