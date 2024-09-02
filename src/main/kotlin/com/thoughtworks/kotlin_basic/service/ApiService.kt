package com.thoughtworks.kotlin_basic.service

import com.thoughtworks.kotlin_basic.model.Inventory
import com.thoughtworks.kotlin_basic.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/products")
    suspend fun getAllProducts(): Call<List<Product>>

    @GET("/inventories")
    suspend fun getAllInventories(): Call<List<Inventory>>
}
