package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model

data class Review(
    val reviewId: Int,
    val productId: Int,
    val reviewer: String,
    val rating: Int,
    val comment: String
)