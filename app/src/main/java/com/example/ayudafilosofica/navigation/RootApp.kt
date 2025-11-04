package com.example.ayudafilosofica.navigation

// Imports de Compose y Navigation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.IconButton


import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController



import com.example.ayudafilosofica.feature.settings.ui.SettingsScreen
import com.example.ayudafilosofica.feature.auth.ui.components.PhilosophiesScreen
import com.example.ayudafilosofica.feature.home.ui.HomeScreen


import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api




import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ayudafilosofica.domain.PhilosophyRepository
import com.example.ayudafilosofica.feature.auth.presentation.PhilosophyViewModel
import com.example.ayudafilosofica.feature.home.presentation.HomeViewModel


private val mapaTitulos = mapOf(
    Destinations.Chat to "Chat",
    Destinations.Philosophies to "Filosofias",
    Destinations.Settings to "Ajustes"

)


//Esto indica que alguna funcion es experimetnal y en un futuro puede cambiar, no afecta a la seguridad ni al rendimiento
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootApp() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState()?.value?.destination

    //Vamos a añadir variables para que cambie el titulo y asaber en que ruta estamos
    val currentRoute = currentDestination?.route
    val tituloBarra = mapaTitulos[currentRoute] ?: "App Filosofia"

    //necesitamos saber si esta aierto el menu, para ello usamos
    var menuAbierto by remember { mutableStateOf(false) }

    fun navTo(route: String) {
        //Cerrar el menu
        menuAbierto = false

        //Comprobar si esta en la ruta que queremos abir o algunos de sus hijos
        val yaEstamos = currentDestination?.hierarchy?.any() { it.route == route } == true
        //Debemos inpedir que abra mas pestañas si ya esta
        if (yaEstamos) return

        //Navegar por las pestañas
        navController.navigate(route) {
            //Evitamos acumular pestañas ue ya tenemos abiertas
            popUpTo(navController.graph.startDestinationId) {
                //Guardamos el estado para textos o scroll, etc..
                saveState = true
            }
            //Evitamos que se abran 20 copias de home o el que sea
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(tituloBarra)},
                navigationIcon = {
                    Box{
                        IconButton(onClick = {menuAbierto = true}){
                            Icon(Icons.Default.MoreVert, contentDescription = "abrir menu")

                        }
                    }
                    DropdownMenu(
                        expanded = menuAbierto,
                        onDismissRequest = { menuAbierto = false}){

                        DropdownMenuItem(
                            text = {Text("chat")},
                            onClick = {
                                navTo(Destinations.Chat)
                            })
                        DropdownMenuItem(
                            text = {Text("Ajustes")},
                            onClick = {
                                navTo(Destinations.Settings)
                            })
                        DropdownMenuItem(
                            text = {Text("Filosofias")},
                            onClick = {
                                navTo(Destinations.Philosophies)
                            })
                    }
                })
        }
    ){innerPading ->
        NavHost(
            navController = navController,
            startDestination = Destinations.Chat,
            modifier = Modifier.padding(innerPading)
        ){
            composable(Destinations.Philosophies) {val items = remember {
                mutableStateListOf(
                    com.example.ayudafilosofica.domain.Philosophy("stoicism", "Estoicismo", false),
                    com.example.ayudafilosofica.domain.Philosophy("epicurean", "Epicureísmo", true),
                    com.example.ayudafilosofica.domain.Philosophy("aristotle", "Aristóteles", false)
                )
            }

                // Debes pasar onToggle: actualiza el item en la lista local
                val onToggle: (String, Boolean) -> Unit = { id, checked ->
                    val idx = items.indexOfFirst { it.id == id }
                    if (idx >= 0) items[idx] = items[idx].copy(isSelected = checked)
                }

                // Llama a tu pantalla pasando los argumentos POR NOMBRE
                com.example.ayudafilosofica.feature.auth.ui.components.PhilosophiesScreen(
                    items = items,
                    onToggle = onToggle
                )}
            composable(Destinations.Chat) {backStackEntry ->
                val vm: HomeViewModel = hiltViewModel(backStackEntry)
                HomeScreen(homeViewModel = vm)
            }
            composable (Destinations.Settings){SettingsScreen()}
        }

    }

}


