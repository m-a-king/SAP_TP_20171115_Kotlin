package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.api.AppleProductApi
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.FullProductInfo
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.ProductImage
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Product
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Review
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Specification
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppleProductViewModel() : ViewModel() {
    private val SERVER_URL = "https://port-0-sap-tp-20171115-5yc2g32mlomgom8g.sel5.cloudtype.app/"
    private val productApi: AppleProductApi

    private val _appleProductList = MutableLiveData<List<Product>>()
    val appleProductList: LiveData<List<Product>>
        get() = _appleProductList

    private val _appleProductProductImageList = MutableLiveData<List<ProductImage>>()
    val appleProductProductImageList: LiveData<List<ProductImage>>
        get() = _appleProductProductImageList

    private val _appleProductSpecList = MutableLiveData<List<Specification>>()
    val appleProductSpecList: LiveData<List<Specification>>
        get() = _appleProductSpecList

    private val _appleProductReviewList = MutableLiveData<List<Review>>()
    val appleProductReviewList: LiveData<List<Review>>
        get() = _appleProductReviewList

    private val _fullProductInfoList = MutableLiveData<List<FullProductInfo>>()
    val fullProductInfoList: LiveData<List<FullProductInfo>>
        get() = _fullProductInfoList


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        productApi = retrofit.create(AppleProductApi::class.java)

        fetchAllImages()
        fetchAllProducts()
        fetchAllSpecs()
        fetchReviews()
    }

    private fun fetchAllProducts() {
        viewModelScope.launch {
            try {
                val response = productApi.getAllProducts()
                _appleProductList.value = response
                updateFullProductInfoList()
            } catch (e: Exception) {
                Log.e("fetchAllAppleProducts", "Error during fetchAllAppleProducts: ${e.message}")
            }
        }
    }

    private fun fetchAllImages() {
        viewModelScope.launch {
            try {
                val response = productApi.getAllImages()
                _appleProductProductImageList.value = response
                updateFullProductInfoList()
            } catch (e: Exception) {
                Log.e("fetchAllImages", "Error during fetchAllImages: ${e.message}")
            }
        }
    }

    private fun fetchAllSpecs() {
        viewModelScope.launch {
            try {
                val response = productApi.getAllSpecs()
                _appleProductSpecList.value = response
                updateFullProductInfoList()
            } catch (e: Exception) {
                Log.e("fetchAllSpecs", "Error during fetchAllSpecs: ${e.message}")
            }
        }
    }

    private fun fetchReviews() {
        viewModelScope.launch {
            try {
                val response = productApi.getAllReviews()
                _appleProductReviewList.value = response
            } catch (e: Exception) {
                Log.e("fetchReviews", "Error during fetchReviews: ${e.message}")
            }
        }
    }

    private fun updateFullProductInfoList() {
        val products = _appleProductList.value ?: emptyList()
        val images = _appleProductProductImageList.value ?: emptyList()
        val specs = _appleProductSpecList.value ?: emptyList()
        val reviews = _appleProductReviewList.value ?: emptyList()

        val combinedList = products.map { product ->
            FullProductInfo(
                product = product,
                productImage = images.find { it.productId == product.productId },
                specification = specs.find { it.productId == product.productId },
                reviews = reviews.filter { it.productId == product.productId }
            )
        }

        _fullProductInfoList.value = combinedList
    }
}
