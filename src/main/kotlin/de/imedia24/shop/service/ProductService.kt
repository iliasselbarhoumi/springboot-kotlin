package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {
        return productRepository.findBySku(sku)?.toProductResponse()
    }

    fun findProductEntityBySku(sku: String): ProductEntity? {
        return productRepository.findBySku(sku)
    }

    fun findProductBySkus(skus: List<String>): List<ProductResponse>? {
        return productRepository.findBySkuIn(skus)?.map { it.toProductResponse() }
    }

    fun saveProduct(productEntity: ProductEntity): ProductResponse {
        return productRepository.save(productEntity).toProductResponse()
    }

    fun findAllProducts() : List<ProductResponse>? {
        return productRepository.findAll()?.map { it.toProductResponse() }
    }
}
