package de.imedia24.shop.db.entity

import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    @Column(name = "sku", nullable = false)
    var sku: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "price", nullable = false)
    var price: BigDecimal,

    @Column(name = "stock", nullable = false)
    var stock: BigDecimal,

    @UpdateTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: ZonedDateTime
){
    constructor() : this("",
        "",
        "",
        BigDecimal.ZERO,
        BigDecimal.ONE,
        ZonedDateTime.now(),
        ZonedDateTime.now()) {}

    constructor(sku: String, name: String, description: String, price: BigDecimal, stock: BigDecimal) : this(
        sku,
        name,
        description,
        price,
        stock,
        ZonedDateTime.now(),
        ZonedDateTime.now()) {}
}
