package com.thoughtworks.kotlin_basic.model

enum class ProductType {
    NORMAL, HIGH_DEMAND
}

data class Product(
    val id: Int,
    val SKU: String,
    val name: String,
    val price: Float,
    val image: String,
    val type: ProductType,
)
