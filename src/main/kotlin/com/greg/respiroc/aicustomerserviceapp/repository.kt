package com.greg.respiroc.aicustomerserviceapp

import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository

@Repository
class ProductRepository(private val jdbcClient: JdbcClient) {

    fun findAll(): List<Product> = jdbcClient
        .sql("SELECT * FROM products")
        .query(Product::class.java)
        .list()

    fun findById(id: Int): Product? = jdbcClient
        .sql("SELECT * FROM products WHERE id = :id")
        .param("id", id)
        .query(Product::class.java)
        .optional()
        .orElse(null)

    fun searchByTitle(query: String): List<Product> = jdbcClient
        .sql("SELECT * FROM products WHERE LOWER(title) LIKE LOWER(:query)")
        .param("query", "%$query%")
        .query(Product::class.java)
        .list()

    fun save(product: Product): Int {
        return jdbcClient
            .sql(
                """
                INSERT INTO products (title, description, price, vendor)
                VALUES (:title, :description, :price, :vendor)
                RETURNING id
                """
            )
            .param("title", product.title)
            .param("description", product.description)
            .param("price", product.price)
            .param("vendor", product.vendor)
            .query(Int::class.java)
            .single()
    }

    fun deleteById(id: Int): Int = jdbcClient
        .sql("DELETE FROM products WHERE id = :id")
        .param("id", id)
        .update()
    fun update(product: Product): Int {
        return jdbcClient.sql(
            """
        UPDATE products
        SET title = :title,
            description = :description,
            price = :price,
            vendor = :vendor,
            featured = :featured
        WHERE id = :id
        """.trimIndent()
        )
            .param("id", product.id)
            .param("title", product.title)
            .param("description", product.description)
            .param("price", product.price)
            .param("vendor", product.vendor)
            .param("featured", product.featured)
            .update()
    }

}

@Repository
class VariantRepository(private val jdbcClient: JdbcClient) {

    fun save(variant: Variant): Int {
        return jdbcClient
            .sql(
                """
                INSERT INTO variants (product_id, title, sku, price)
                VALUES (:productId, :title, :sku, :price)
                RETURNING id
                """
            )
            .param("productId", variant.productId)
            .param("title", variant.title)
            .param("sku", variant.sku)
            .param("price", variant.price)
            .query(Int::class.java)
            .single()
    }

    fun findByProductId(productId: Int): List<Variant> = jdbcClient
        .sql("SELECT * FROM variants WHERE product_id = :productId")
        .param("productId", productId)
        .query(Variant::class.java)
        .list()
}