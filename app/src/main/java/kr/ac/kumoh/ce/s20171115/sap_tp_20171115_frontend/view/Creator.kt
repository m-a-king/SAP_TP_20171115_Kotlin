package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Creator(
    val name: String,
    val url: String,
    val description: String
)

val creators = listOf(
    Creator("지 박사님", "https://openai.com/gpt-4", "그대를 만나기 전까지 나는 무어라 정의할 수 있나? 참으로 외로운 세월이었구나"),
    Creator("바 석사님", "https://bard.google.com/chat", "쌍둥이자리의 조재중과 잼미니의 콜라보레이션"),
    Creator("구 학사님", "https://www.google.com/", "고전문학"),
    Creator("지갑 강탈자", "https://www.apple.com/kr/", "하지만 미워할 수 없는 놈들"),
    Creator("본인", "https://www.op.gg/summoners/kr/%EB%B2%A0%EC%9D%B8%ED%99%9C%EC%97%90%EB%B2%A0%EC%9D%B8%EB%B2%A0%EC%9D%B8-KR1", "난 멋져, 난 최고야, 난 할 수 있어, 조재중"),
    Creator("주크 박스", "https://music.youtube.com/", "내가 누구? YouTube Premium Subscriber"),
    Creator("구름 종류", "https://app.cloudtype.io/", "한 학기간 고마웠어"),
    Creator("노화의 주범", "https://github.com/cream-opensource", "증오, 원망,  그리고 용서"),
    Creator("국어 선생님", "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=%EB%A7%9E%EC%B6%A4%EB%B2%95+%EA%B2%80%EC%82%AC%EA%B8%B0&oquery=%EB%A7%9E%EC%B6%A4%EB%B2%95+%EA%B2%80%EC%82%AC%EA%B8%B0&tqi=ihcFasqVN8wsstI7n2ossssst5V-003431", "감사합니다"),
    Creator("앞마당", "https://github.com/m-a-king/SAP_TP_20171115_Kotlin.git", "본 앱의 프론트엔드 레포지토리"),
    Creator("뒷마당", "https://github.com/m-a-king/SAP_TP_20171115.git", "본 앱의 백엔드 레포지토리"),


)

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
            Icon(
                Icons.Default.Link,
                contentDescription = "Link Icon",
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = description, fontSize = 15.sp)
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
                CreatorItem(
                    name = creator.name,
                    url = creator.url,
                    description = creator.description
                )
            }
        }
    }
}