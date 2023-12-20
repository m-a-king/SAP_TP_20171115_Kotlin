package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.FullProductInfo
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.model.Review

enum class AppleProductScreen {
    Product, Review, Creator
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppleProductApp(fullProductsInfoList: List<FullProductInfo>) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        DrawerSheet(drawerState, navController, scope)
    }) {
        Scaffold(topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(222, 222, 222), Color(22, 22, 22)
                            )
                        )
                    )
            ) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Row(
                                modifier = Modifier
                                    .clickable { navController.navigate(AppleProductScreen.Product.name) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                ProductIcon(50)
                                Spacer(Modifier.width(8.dp))
                                Text("appleProducts: ", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.W600)
                            }

                            // "TermProject" 부분에는 클릭 이벤트를 적용하지 않음, 코틀린 문법으로 앱 타이틀 정함!
                            Text("TermProject", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.W400)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }, modifier = Modifier.size(40.dp)) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "드로어바 아이콘",
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Transparent, // 배경색 투명
                        titleContentColor = Color.Black
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
                                productId = productId,
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
fun AppleProductList(
    navController: NavController,
    fullProductInfos: List<FullProductInfo>,
    innerPadding: PaddingValues
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf(
        "All",
        "Smartphone",
        "Laptop",
        "Smartwatch",
        "Earbuds",
        "Headphones",
        "Tablet",
        "Desktop",
        "Monitor"
    )

    Column(modifier = Modifier.padding(innerPadding)) {
        // 카테고리 선택 UI
        CategoryDropdownMenu(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { category -> selectedCategory = category }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(222, 222, 222), Color(22, 22, 22))
                    )
                )
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                // 필터링된 제품 목록
                val filteredProducts = if (selectedCategory == "All") {
                    fullProductInfos
                } else {
                    fullProductInfos.filter { it.product.category == selectedCategory }
                }

                items(filteredProducts) { fullProductInfo ->
                    AppleProduct(
                        navController = navController,
                        appleProduct = fullProductInfo.product,
                        appleProductProductImage = fullProductInfo.productImage,
                        appleProductSpec = fullProductInfo.specification
                    )
                }
            }
        }
    }
}