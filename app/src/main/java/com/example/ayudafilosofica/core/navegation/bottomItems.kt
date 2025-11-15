package com.example.ayudafilosofica.core.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ayudafilosofica.feature.navigation.Destinations

data class BottomItem(val route: String, val label: String, val icon: ImageVector)

val bottonsItem = listOf(
    BottomItem(Destinations.Philosophies, "Filosofía", Icons.Default.Edit),
    BottomItem(Destinations.Chat, "chat", Icons.Default.Home),
)
