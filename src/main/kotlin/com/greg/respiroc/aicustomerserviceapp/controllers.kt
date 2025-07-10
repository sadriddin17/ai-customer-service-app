package com.greg.respiroc.aicustomerserviceapp

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
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

    @GetMapping("/{id}/edit")
    fun editForm(@PathVariable id: Int, model: Model): String {
        val product = productService.getById(id)
            ?: return "redirect:/"
        model.addAttribute("product", product)
        return "edit"
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @ModelAttribute product: Product): String {
        productService.deleteProduct(id)
        productService.addProduct(product.copy(id = id))
        return "redirect:/"
    }
}

@Controller
@RequestMapping("/ui/products")
class UiProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun list(model: Model): String {
        model.addAttribute("products", productService.getAll())
        return "fragments/product-table"
    }

    @PostMapping
    fun add(@ModelAttribute product: Product, model: Model): String {
        productService.addProduct(product)
        model.addAttribute("products", productService.getAll())
        return "fragments/product-table"
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int, model: Model): String {
        productService.deleteProduct(id)
        model.addAttribute("products", productService.getAll())
        return "fragments/product-table"
    }

    @GetMapping("/search")
    fun search(@RequestParam query: String, model: Model): String {
        model.addAttribute("products", productService.search(query))
        return "fragments/product-search-results"
    }

    @GetMapping("/export")
    fun export(response: HttpServletResponse) {
        val products = productService.getAll()
        response.contentType = "text/csv"
        response.setHeader("Content-Disposition", "attachment; filename=products.csv")

        response.writer.use { writer ->
            writer.println("ID,Title,Description,Price,Vendor")
            products.forEach {
                writer.println("${it.id},\"${it.title}\",\"${it.description}\",${it.price},\"${it.vendor}\"")
            }
        }
    }
}

@Controller
class HomeController {

    @GetMapping("/")
    fun index(): String = "index"
}