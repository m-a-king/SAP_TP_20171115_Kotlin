package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.api

import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.ProductImage
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Product
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Review
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Specification
import retrofit2.http.GET
import retrofit2.http.Path

interface AppleProductApi {
    @GET("/product/{productIdx}")
    suspend fun getProduct(@Path("productIdx") productIdx: Int): Product

    @GET("/product/all")
    suspend fun getAllProducts(): List<Product>

    @GET("/image/{productIdx}")
    suspend fun getImage(@Path("productIdx") productIdx: Int): ProductImage

    @GET("/image/all")
    suspend fun getAllImages(): List<ProductImage>

    @GET("/spec/{productIdx}")
    suspend fun getSpec(@Path("productIdx") productIdx: Int): Specification

    @GET("/spec/all")
    suspend fun getAllSpecs(): List<Specification>

    @GET("/review/{productIdx}")
    suspend fun getReviews(@Path("productIdx") productIdx: Int): List<Review>

    @GET("/review/all")
    suspend fun getAllReviews(): List<Review>


}