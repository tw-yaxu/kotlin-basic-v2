package com.thoughtworks.kotlin_basic.dto

data class ProductWithInventoryDTO (
    val SKU: String,
    val name: String,
    val price: Float,
    val stockQuantity: Int,
    val image: String,
)