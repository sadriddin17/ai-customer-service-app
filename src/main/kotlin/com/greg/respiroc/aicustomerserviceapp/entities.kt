package com.greg.respiroc.aicustomerserviceapp

data class Product(
    val id: Int? = null,
    val title: String,
    val description: String?,
    val price: Double?,
    val vendor: String?,
    var featured: Boolean = false
)

data class Variant(
    val id: Int? = null,
    val productId: Int,
    val title: String?,
    val sku: String?,
    val price: Double?
)