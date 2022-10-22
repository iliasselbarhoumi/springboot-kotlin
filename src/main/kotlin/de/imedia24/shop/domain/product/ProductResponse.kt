package de.imedia24.shop.domain.product

import de.imedia24.shop.db.entity.ProductEntity
import java.math.BigDecimal

data class ProductResponse(
    var sku: String,
    var name: String,
    var description: String,
    var price: BigDecimal,
    var stock: BigDecimal
) {
    companion object {
        fun ProductEntity.toProductResponse() = ProductResponse(
            sku = sku,
            name = name,
            description = description ?: "",
            price = price?: BigDecimal.ZERO,
            stock = stock?: BigDecimal.ONE
        )
    }
}
