package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model

data class Specification(
    val specId: Int,
    val productId: Int,
    val processor: String,
    val memory: String,
    val storage: String,
    val display: String,
    val color: String,
    val battery: String,
    val camera: String,
    val os: String,
)