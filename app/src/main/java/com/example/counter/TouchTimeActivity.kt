package com.example.counter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.edit
import com.example.counter.ui.theme.CounterTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class TouchTimeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CounterTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        val kstZoneId = ZoneId.of("Asia/Seoul")
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        val activity = LocalContext.current as? Activity
        val sharedPref = remember {
            activity?.getPreferences(Context.MODE_PRIVATE)
        }

        var formattedTime by remember {
            mutableStateOf(sharedPref?.getString("lastTime", "") ?: "")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = formattedTime)
            Button(onClick = {
                val nowInKST = Instant.now().atZone(kstZoneId).format(formatter)
                formattedTime = nowInKST
                sharedPref?.edit { putString("lastTime", nowInKST) }
            }) {
                Text(text = "시간 기록 버튼")
            }
        }
    }



    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        CounterTheme {
            MainScreen()
        }
    }
}