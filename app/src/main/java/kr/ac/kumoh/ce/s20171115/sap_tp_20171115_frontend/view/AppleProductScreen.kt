package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.R
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.FullProductInfo
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Product
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.ProductImage
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Review
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Specification

enum class AppleProductScreen {
    Product, Review, Creator
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppleProductApp(fullProductsInfoList: List<FullProductInfo>) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        DrawerSheet(drawerState, navController)
    }) {
        Scaffold(topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(222, 222, 222), Color(22, 22, 22)
                            ) // 여기에 원하는 그라데이션 색상을 설정하세요
                        )
                    )
            ) {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(AppleProductScreen.Product.name)
                                }, contentAlignment = Alignment.Center
                        ) {
                            ProductIcon(50)
                        }
                    }, colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Transparent, // 배경색 투명
                        titleContentColor = Color.Black // 텍스트 색상 설정
                    )
                )
            }
        }) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = AppleProductScreen.Product.name,
            ) {
                composable(route = AppleProductScreen.Product.name) {
                    AppleProductList(navController, fullProductsInfoList, innerPadding)
                }
                composable(
                    route = AppleProductScreen.Review.name + "/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                    if (productId >= 0) {
                        val reviewsForProduct =
                            fullProductsInfoList.find { it.product.productId == productId }?.reviews

                        if (reviewsForProduct != null && reviewsForProduct.isNotEmpty()) {
                            ReviewList(reviewsForProduct, innerPadding)
                        } else {
                            val noReviewAvailable = Review(
                                reviewId = -1, // 임시 ID
                                productId = productId, // 현재 조회 중인 제품의 ID
                                reviewer = "시스템 메시지", rating = 0, comment = "아직 리뷰가 없습니다."
                            )

                            val noReviewList = listOf(noReviewAvailable) // 단일 Review 객체를 리스트로 변환
                            ReviewList(noReviewList, innerPadding)
                        }
                    }
                }
                composable(route = AppleProductScreen.Creator.name) {
                    CreatorScreen(innerPadding)
                }
            }
        }
    }
}

@Composable
fun CreatorItem(name: String, url: String, description: String) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                context.startActivity(intent)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Link, contentDescription = "Link Icon", modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = description, fontSize = 15.sp) // 한 줄 설명
            }
        }
    }
}

@Composable
fun CreatorScreen(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(222, 222, 222), Color(22, 22, 22)) // 그라데이션
                )
            )
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = innerPadding
        ) {
            this.items(creators) { creator ->
                CreatorItem(name = creator.name, url = creator.url, description = creator.description)
            }
        }
    }
}


@Composable
fun DrawerSheet(
    drawerState: DrawerState, navController: NavController
) {
    val scope = rememberCoroutineScope()
    ModalDrawerSheet {

        NavigationDrawerItem(icon = { ProductIcon(33) },
            label = { Text("애플 제품 리스트") },
            selected = false,
            onClick = {
                navController.navigate(AppleProductScreen.Product.name)
                scope.launch {
                    drawerState.close()
                }
            })
        NavigationDrawerItem(icon = { PeopleIcon() },
            label = { Text("제작에 도움을 주신 분들") },
            selected = false,
            onClick = {
                navController.navigate(AppleProductScreen.Creator.name)
                scope.launch {
                    drawerState.close()
                }
            })
    }
}

@Composable
fun ProductIcon(iconSize: Int) {
    Image(
        painter = painterResource(id = R.drawable.apple_logo_black),
        contentDescription = "제품 리스트, 사과",
        modifier = Modifier.size(iconSize.dp)
    )
}

@Composable
fun PeopleIcon() {
    Icon(
        imageVector = Icons.Default.People,
        contentDescription = "제작자들",
        tint = Color.Black,
        modifier = Modifier.size(33.dp)
    )
}

@Composable
fun AccountCircleIcon() {
    Icon(
        imageVector = Icons.Default.AccountCircle,
        contentDescription = "사용자",
        tint = Color.White,
        modifier = Modifier.size(33.dp)
    )
}

@Composable
fun AppleProductList(
    navController: NavController,
    fullProductInfos: List<FullProductInfo>,
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
//                brush = Brush.verticalGradient(
//                brush = Brush.radialGradient(
                    colors = listOf(Color(222, 222, 222), Color(22, 22, 22)) // 그라데이션
                )
            )
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 8.dp, // 탑바와 첫 아이템의 간격
                bottom = innerPadding.calculateBottomPadding(), start = 8.dp, end = 8.dp
            )
        ) {
            items(fullProductInfos) { fullProductInfo ->
                AppleProduct(
                    navController = navController,
                    appleProduct = fullProductInfo.product,
                    appleProductProductImage = fullProductInfo.productImage,
                    appleProductSpec = fullProductInfo.specification,

                    )
            }
        }
    }
}


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
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, // 세로축 중앙 정렬
        horizontalAlignment = Alignment.CenterHorizontally // 가로축 중앙 정렬
    ) {
        Text("${appleProduct.model}", fontSize = 25.sp, color = Color(235, 235, 235)) // 제품명
        Text("카테고리: ${appleProduct.category}", fontSize = 15.sp, color = Color(235, 235, 235))

        //formatReleaseDate를 사용하기 위해서 안드로이드 버전 코드가 0보다 높아야함 (오레오 26)
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
    AsyncImage(
        model = productImage.imageUrl,
        contentDescription = productImage.description,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(percent = 10))
    )
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
            modifier = Modifier.weight(1f),  // 스펙이 남은 공간을 차지하도록 설정
            verticalArrangement = Arrangement.Top
        ) {
            appleProductSpec?.let { spec ->
                ProductSpecs(spec)
            }
        }
        Column(
            horizontalAlignment = Alignment.End  // 리뷰 버튼을 오른쪽에 정렬
        ) {
            ReviewButton(navController, appleProduct)
        }
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
fun ReviewButton(navController: NavController, appleProduct: Product) {
    Button(

        onClick = {
            navController.navigate(AppleProductScreen.Review.name + "/${appleProduct.productId}")
        }, colors = ButtonDefaults.buttonColors(
            containerColor = Color(105, 105, 105), // 버튼 배경색
            contentColor = Color.White    // 버튼 내용 색상
        )
    ) {
        Text("리뷰 보기", fontSize = 15.sp)
    }

}

@Composable
fun ReviewList(reviews: List<Review>, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Column이 사용 가능한 모든 공간을 채우도록 설정
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
                        Spacer(modifier = Modifier.width(8.dp)) // 아이콘과 텍스트 사이 간격
                        Text(
                            text = review.reviewer,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W500,
                            color = Color(235, 235, 235)
                        )
                    }
                }

                // 사과 그림을 포함하는 Box
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