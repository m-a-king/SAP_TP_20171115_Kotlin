package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Product
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Review

@Composable
fun ReviewButton(navController: NavController, appleProduct: Product) {
    Button(

        onClick = {
            navController.navigate(AppleProductScreen.Review.name + "/${appleProduct.productId}")
        }, colors = ButtonDefaults.buttonColors(
            containerColor = Color(105, 105, 105),
            contentColor = Color.White
        )
    ) {
        Text("리뷰 보기", fontSize = 15.sp)
    }

}

@Composable
fun ReviewList(reviews: List<Review>, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(222, 222, 222), Color(22, 22, 22)) // 그라데이션
                ),
            )
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
            )

    ) {
        reviews.forEach { review ->
            ReviewItem(review) // 개별 리뷰 아이템을 표시
        }
    }
}


@Composable
fun ReviewItem(review: Review) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color(105, 105, 105))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.weight(1f) // 여유 공간 모두 사용
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AccountCircleIcon()
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = review.reviewer,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W500,
                            color = Color(235, 235, 235)
                        )
                    }
                }

                // applePoint (starPoint -> 별점)
                Box {
                    ReviewRating(review.rating)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = review.comment, fontSize = 20.sp, color = Color(235, 235, 235))
        }
    }
}

@Composable
fun ReviewRating(rating: Int) {
    Row {
        for (i in 1..rating) {
            ProductIcon(20)
        }
    }
}