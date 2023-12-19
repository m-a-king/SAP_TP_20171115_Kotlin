package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Product(
    val productId: Int,
    val model: String,
    val category: String,
    val releaseDate: String

) {
    // 안드로이드 버전 0 이상 필요
    @RequiresApi(Build.VERSION_CODES.O)
    // 날짜 형식 변경 함수
    fun formatReleaseDate(isoDate: String): String {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val date = LocalDate.parse(isoDate, formatter)
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }


}


