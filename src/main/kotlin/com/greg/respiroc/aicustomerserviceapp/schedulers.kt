package com.greg.respiroc.aicustomerserviceapp

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.net.URL

@Component
class ProductImportScheduler(
    private val productRepository: ProductRepository,
    private val variantRepository: VariantRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val mapper = jacksonObjectMapper()

    @Scheduled(initialDelay = 0, fixedDelay = Long.MAX_VALUE)
    @Transactional
    fun fetchProducts() {
        val url = URL("https://famme.no/products.json")
        log.info("Fetching products from famme.no")

        val rootNode = mapper.readTree(url)
        val productsNode = rootNode.get("products")

        if (productsNode == null || !productsNode.isArray) {
            log.warn("Invalid JSON structure")
            return
        }

        val products: List<FammeProductDto> = mapper.readValue(productsNode.toString())
        val limitedProducts = products.take(50)

        for (p in limitedProducts) {
            val product = Product(
                title = p.title,
                description = p.body_html,
                price = p.variants.firstOrNull()?.price?.toDoubleOrNull(),
                vendor = p.vendor
            )
            val productId = productRepository.save(product)

            p.variants.forEach { variantDto ->
                val variant = Variant(
                    productId = productId,
                    title = variantDto.title,
                    sku = variantDto.sku,
                    price = variantDto.price?.toDoubleOrNull()
                )
                variantRepository.save(variant)
            }
        }

        log.info("Saved ${limitedProducts.size} products with variants")
    }
}