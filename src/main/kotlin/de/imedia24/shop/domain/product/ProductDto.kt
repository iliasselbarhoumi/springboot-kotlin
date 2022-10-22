package de.imedia24.shop.domain.product

import de.imedia24.shop.db.entity.ProductEntity
import java.math.BigDecimal

class ProductDto(
    val sku: String,
    val name: String,
    val description: String,
    val price: BigDecimal = BigDecimal.ZERO,
    val stock: BigDecimal = BigDecimal.ONE
){}

fun ProductDto.convertToModel() = ProductEntity(
    sku = this.sku,
    name = this.name,
    description = this.description,
    price = this.price,
    stock = this.stock
)
