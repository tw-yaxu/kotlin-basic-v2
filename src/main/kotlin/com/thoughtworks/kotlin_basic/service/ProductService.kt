package com.thoughtworks.kotlin_basic.service

import com.thoughtworks.kotlin_basic.ApiClient
import com.thoughtworks.kotlin_basic.dto.ProductWithInventoryDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ProductService(private val apiClient: ApiClient) {
    suspend fun getAllProductWithInventory(): List<ProductWithInventoryDTO> = withContext(Dispatchers.IO) {

        val productsDeferred = async { apiClient.apiService.getAllProducts() }
        val inventoriesDeferred = async { apiClient.apiService.getAllInventories() }

        val products = productsDeferred.await()
        val inventories = inventoriesDeferred.await()

        val inventoryQuantityMap = inventories.groupBy { it.SKU }.mapValues { entry -> entry.value.sumOf { it.quantity } }
        val productWithInventoryList:List<ProductWithInventoryDTO> = products.map {
            val stockQuantity = inventoryQuantityMap[it.SKU] ?: 0
            val price: Float = if (stockQuantity <= 30) (it.price * 1.5).toFloat() else if (stockQuantity <= 100) (it.price * 1.2).toFloat() else it.price
            ProductWithInventoryDTO(
                SKU = it.SKU,
                name = it.name,
                price = price,
                image = it.image,
                stockQuantity = stockQuantity
                )
        }
        return@withContext productWithInventoryList
    }
}