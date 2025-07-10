package com.greg.respiroc.aicustomerserviceapp

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun getAll(): List<Product> = productService.getAll()

    @GetMapping("/search")
    fun search(@RequestParam query: String): List<Product> = productService.search(query)

    @PostMapping
    fun add(@RequestBody dto: ProductDto): ResponseEntity<Product> {
        val product = Product(
            title = dto.title,
            description = dto.description,
            price = dto.price,
            vendor = dto.vendor
        )
        return ResponseEntity.ok(productService.addProduct(product))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<Product> {
        return productService.getById(id)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }
}