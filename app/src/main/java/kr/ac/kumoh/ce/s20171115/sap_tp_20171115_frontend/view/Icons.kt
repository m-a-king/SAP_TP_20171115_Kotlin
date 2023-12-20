package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.R

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
