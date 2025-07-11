package com.greg.respiroc.aicustomerserviceapp

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val variantRepository: VariantRepository
) {

    fun getAll(): List<Product> = productRepository.findAll()

    fun search(query: String): List<Product> = productRepository.searchByTitle(query)

    @Transactional
    fun addProduct(product: Product): Product {
        val id = productRepository.save(product)
        return product.copy(id = id)
    }

    @Transactional
    fun deleteProduct(id: Int) {
        productRepository.deleteById(id)
    }

    fun getById(id: Int): Product? = productRepository.findById(id)

    fun update(product: Product) = productRepository.update(product)
}