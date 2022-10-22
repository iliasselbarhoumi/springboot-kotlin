package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductDto
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.convertToModel
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("/products", produces = ["application/json;charset=utf-8"])
    fun findAllProducts(): ResponseEntity<List<ProductResponse>>  {
        logger.info("Request for all products ")
        val productsList = productService.findAllProducts()
        return if(productsList == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(productsList)
        }
    }

    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductsBySku(@PathVariable("sku") sku: String): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")
        val product = productService.findProductBySku(sku)
        return if(product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }

    @GetMapping("/product/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductsBySkuList(@PathVariable("sku") skus: List<String>): ResponseEntity<List<ProductResponse>>  {
        logger.info("Request for product ${skus.toString()}")
        val productsList = productService.findProductBySkus(skus)
        return if(productsList == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(productsList)
        }
    }

    @PostMapping("/product", produces = ["application/json;charset=utf-8"])
    fun saveProduct(@RequestBody productDto: ProductDto) : ResponseEntity<ProductResponse> {
        logger.info("Request to save product ${productDto.sku}")
        val savedProduct = productService.saveProduct(productDto.convertToModel())
        return if(savedProduct == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(savedProduct)
        }
    }

    @PatchMapping("/product/{sku}")
    fun updateProduct(@PathVariable("sku") sku: String, @RequestBody productDto: ProductDto) : ResponseEntity<ProductResponse> {
        logger.info("Request to partially update product ${productDto.sku}")
        val productToUpdate = productService.findProductEntityBySku(sku)
        return if (productToUpdate != null) {
            productToUpdate.name = productDto.name
            productToUpdate.description = productDto.description
            productToUpdate.price = productDto.price
            val updatedProduct = productService.saveProduct(productToUpdate)
            if (updatedProduct == null){
                ResponseEntity.notFound().build()
            } else {
                ResponseEntity.ok(updatedProduct)
            }
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
