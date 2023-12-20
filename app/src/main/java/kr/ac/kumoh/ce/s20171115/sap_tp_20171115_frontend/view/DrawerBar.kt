package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.view

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerSheet(
    drawerState: DrawerState, navController: NavController, scope: CoroutineScope
) {

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