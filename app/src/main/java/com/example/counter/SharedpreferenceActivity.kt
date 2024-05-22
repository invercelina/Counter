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

class SharedpreferenceActivity : ComponentActivity() {
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
        val activity = LocalContext.current as? Activity
        val sharedPref = remember {
            activity?.getPreferences(Context.MODE_PRIVATE)
        }

        var count by remember {
            val saveCount = sharedPref?.getInt("lastCount", 0)
            mutableStateOf(saveCount ?: 0)
        }
        var overFlowed by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (overFlowed) {
                Text(text = "Overflowed")
            }
            Text(text = "Count : $count")
            Button(onClick = {
                if (count > Int.MAX_VALUE - 1) {
                    overFlowed = true
                } else {
                    count++
                }
                sharedPref?.edit { putInt("lastCount", count) }
            }) {
                Text(text = "1 증가 버튼")
            }
            Button(onClick = {
                count--
                sharedPref?.edit { putInt("lastCount", count) }
                if (overFlowed) {
                    overFlowed = false
                }
            }) {
                Text(text = "1 감소 버튼")
            }
            Button(onClick = {
                count = 0
                sharedPref?.edit { putInt("lastCount", count) }
                if (overFlowed) {
                    overFlowed = false
                }
            }) {
                Text(text = "0 초기화 버튼")
            }
            Button(onClick = {
                if (count > Int.MAX_VALUE / 2) {
                    overFlowed = true
                } else {
                    count *= 2
                    sharedPref?.edit { putInt("lastCount", count) }
                }
            }) {
                Text(text = "2배 버튼")
            }
            Button(onClick = {
                count /= 2
                sharedPref?.edit { putInt("lastCount", count) }
                if (overFlowed) {
                    overFlowed = false
                }
            }) {
                Text(text = "반 나누기 버튼")
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