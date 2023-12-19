package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model

data class FullProductInfo(
    val product: Product,
    val productImage: ProductImage?,
    val specification: Specification?,
    val reviews: List<Review>?
)