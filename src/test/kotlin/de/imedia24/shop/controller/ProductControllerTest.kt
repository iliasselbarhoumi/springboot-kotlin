package de.imedia24.shop.controller


import com.fasterxml.jackson.databind.ObjectMapper
import de.imedia24.shop.db.entity.ProductEntity
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import java.math.BigDecimal
import java.time.ZonedDateTime


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    val baseUrl = "/products"

    @Nested
    @DisplayName("GET /products")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetProducts {
        @Test
        fun `should return all products by skus`() {
            mockMvc.get("/products/1")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType("application/json;charset=utf-8") }
                    jsonPath("sku") {
                        value("1")
                    }
                    jsonPath("name") {
                        value("produit1")
                    }
                }
        }

        @Test
        fun `should return NOT FOUND if the sku does not exist`() {
            val accountNumber = "does_not_exist"
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST /product")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewProduct {
        @Test
        fun `should add new product`() {
            val productToSave = ProductEntity("99", "produit99", "Produit 99", BigDecimal.ONE,
                BigDecimal.ZERO, ZonedDateTime.now(),ZonedDateTime.now())
            mockMvc.post("/product") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(productToSave)
            }
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType("application/json;charset=utf-8")
                        jsonPath("sku") {
                            value("99")
                        }
                    }
                }
            mockMvc.get("$baseUrl/${productToSave.sku}")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType("application/json;charset=utf-8")
                        jsonPath("sku") {
                            value("99")
                        }
                        jsonPath("name") {
                            value("produit99")
                        }
                    }
                }
        }
    }
}
