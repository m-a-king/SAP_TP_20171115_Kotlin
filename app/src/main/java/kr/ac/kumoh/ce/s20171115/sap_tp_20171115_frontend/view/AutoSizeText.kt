package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.view

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun ResponsiveText(text: String, initialFontSize: TextUnit, maxWidth: Dp) {
    val context = LocalContext.current
    var fontSize by remember { mutableStateOf(initialFontSize) }

    val paint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
    }

    fontSize = calculateFontSizeForWidth(text, initialFontSize, maxWidth, paint, context)

    Text(
        text,
        color = Color(222, 222, 222),
        fontSize = fontSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

fun calculateFontSizeForWidth(
    text: String, initialFontSize: TextUnit, maxWidth: Dp, paint: android.graphics.Paint,
    context: Context
): TextUnit {
    val density = context.resources.displayMetrics.density
    var fontSize = initialFontSize.value

    paint.textSize = fontSize * density
    if (paint.measureText(text) <= maxWidth.value * density) {
        return initialFontSize
    }

    while (paint.measureText(text) > maxWidth.value * density && fontSize > 0) {
        fontSize -= 1
        paint.textSize = fontSize * density
    }

    return fontSize.sp
}