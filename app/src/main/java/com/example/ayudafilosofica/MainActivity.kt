package com.example.ayudafilosofica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ayudafilosofica.navigation.RootApp
import com.example.ayudafilosofica.ui.theme.AyudaFilosoficaTheme
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





