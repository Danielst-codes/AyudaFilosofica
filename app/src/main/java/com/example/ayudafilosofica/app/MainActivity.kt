package com.example.ayudafilosofica.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ayudafilosofica.feature.navigation.RootApp
import com.example.ayudafilosofica.app.theme.AyudaFilosoficaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AyudaFilosoficaTheme { RootApp() }
        }
    }
}