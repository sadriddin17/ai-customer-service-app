package com.greg.respiroc.aicustomerserviceapp

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class ProductDto(
    val title: String,
    val description: String?,
    val price: Double?,
    val vendor: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class FammeProductDto(
    val title: String,
    val vendor: String?,
    val body_html: String?,
    val variants: List<FammeVariantDto>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class FammeVariantDto(
    val title: String?,
    val sku: String?,
    val price: String?
)