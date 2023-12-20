package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Product
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.ProductImage
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Specification

@SuppressLint("NewApi")
@Composable
fun AppleProduct(
    navController: NavController,
    appleProduct: Product,
    appleProductProductImage: ProductImage?,
    appleProductSpec: Specification?
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        ProductRow(appleProduct, appleProductProductImage)
        AnimatedVisibility(
            visible = expanded
        ) {
            ProductDetails(appleProductSpec, navController, appleProduct)
        }
    }
}


@Composable
fun ProductRow(
    appleProduct: Product, appleProductProductImage: ProductImage?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color(105, 105, 105))
            .padding(8.dp)
    ) {
        appleProductProductImage?.let { image ->
            ProductImage(image)
        }
        Spacer(modifier = Modifier.width(20.dp))
        ProductInfo(appleProduct)
    }
}




@Composable
fun ProductInfo(appleProduct: Product) {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResponsiveText(
            text = appleProduct.model,
            initialFontSize = 25.sp,
            maxWidth = 222.dp // 최대 너비 설정
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text("카테고리: ${appleProduct.category}", fontSize = 15.sp, color = Color(235, 235, 235))

        //formatReleaseDate를 사용하기 위해서 안드로이드 버전 코드가 0보다 높아야한다는 오류가 났음 (오레오 26)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Text(
                "출시일: ${appleProduct.formatReleaseDate(appleProduct.releaseDate)}",
                fontSize = 15.sp, color = Color(235, 235, 235),
            )
        }
    }
}


@Composable
fun ProductImage(productImage: ProductImage) {
    var sizeState by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = Modifier
            .size(100.dp)
            .onSizeChanged { sizeState = it }
    ) {
        AsyncImage(
            model = productImage.imageUrl,
            contentDescription = productImage.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(sizeState.width.dp, sizeState.height.dp)
                .clip(RoundedCornerShape(percent = 10))
        )
    }
}

@Composable
fun ProductSpecs(spec: Specification) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("프로세서: ${spec.processor}", fontSize = 14.sp)
        Text("메모리: ${spec.memory}", fontSize = 14.sp)
        Text("저장공간: ${spec.storage}", fontSize = 14.sp)
        Text("디스플레이: ${spec.display}", fontSize = 14.sp)
        Text("색상: ${spec.color}", fontSize = 14.sp)
        Text("배터리: ${spec.battery}", fontSize = 14.sp)
        Text("카메라: ${spec.camera}", fontSize = 14.sp)
        Text("운영 체제: ${spec.os}", fontSize = 14.sp)
    }
}

@Composable
fun ProductDetails(
    appleProductSpec: Specification?, navController: NavController, appleProduct: Product
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(222, 222, 222))
            .padding(8.dp)

    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            appleProductSpec?.let { spec ->
                ProductSpecs(spec)
            }
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            ReviewButton(navController, appleProduct)
        }
    }
}