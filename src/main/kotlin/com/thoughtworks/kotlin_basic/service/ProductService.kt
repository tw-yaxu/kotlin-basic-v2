package com.thoughtworks.kotlin_basic.service

import com.thoughtworks.kotlin_basic.dto.ProductWithInventoryDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ProductService(private val apiService: ApiService) {
    suspend fun getAllProductWithInventory(): List<ProductWithInventoryDTO> = withContext(Dispatchers.IO) {

        val productsDeferred = async { apiService.getAllProducts().execute().body() }
        val inventoriesDeferred = async { apiService.getAllInventories().execute().body() }

        val products = productsDeferred.await() ?: emptyList()
        val inventories = inventoriesDeferred.await() ?: emptyList()
        val productWithInventoryList:List<ProductWithInventoryDTO> = products.map {
            ProductWithInventoryDTO(
                SKU = it.SKU,
                name = it.name,
                price = it.price,
                image = it.image,
                stockQuantity = 0
                )
        }
        return@withContext productWithInventoryList
    }
}