package kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.ui.theme.SAP_TP_20171115_frontEndTheme
import kr.ac.kumoh.ce.s20171115.sap_tp_20171115_frontend.viewModel.AppleProductViewModel

class MainActivity : ComponentActivity() {
    private val appleProductViewModel: AppleProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(appleProductViewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: AppleProductViewModel) {
    val fullProductInfos by viewModel.fullProductInfoList.observeAsState(emptyList())

    SAP_TP_20171115_frontEndTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppleProductApp(fullProductInfos)
        }
    }
}


