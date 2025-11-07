package com.example.ayudafilosofica.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ayudafilosofica.feature.settings.ui.SettingsScreen
import com.example.ayudafilosofica.feature.auth.presentation.PhilosophyEvent
import com.example.ayudafilosofica.feature.auth.presentation.PhilosophyViewModel
import com.example.ayudafilosofica.feature.auth.presentation.PhilosophyEffect
import com.example.ayudafilosofica.feature.auth.ui.components.PhilosophiesScreen
import com.example.ayudafilosofica.feature.home.presentation.HomeViewModel
import com.example.ayudafilosofica.feature.home.ui.HomeScreen
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private val mapaTitulos = mapOf(
    Destinations.Chat to "Chat",
    Destinations.Philosophies to "Filosofías",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootApp() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState()?.value?.destination
    val currentRoute = currentDestination?.route
    val tituloBarra = mapaTitulos[currentRoute] ?: "Ayuda Filosófica"

    var menuAbierto by remember { mutableStateOf(false) }

    fun navTo(route: String) {
        menuAbierto = false
        val yaEstamos = currentDestination?.hierarchy?.any { it.route == route } == true
        if (yaEstamos) return
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(tituloBarra) },
                navigationIcon = {
                    Box {
                        IconButton(onClick = { menuAbierto = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "abrir menú")
                        }
                    }
                    DropdownMenu(
                        expanded = menuAbierto,
                        onDismissRequest = { menuAbierto = false }
                    ) {
                        DropdownMenuItem(text = { Text("Chat") }, onClick = { navTo(Destinations.Chat) })
                        DropdownMenuItem(text = { Text("Filosofías") }, onClick = { navTo(Destinations.Philosophies) })
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.Chat,
            modifier = Modifier.padding(innerPadding)
        ) {


            composable(Destinations.Philosophies) { backStackEntry ->
                val vm: PhilosophyViewModel = hiltViewModel(backStackEntry)
                val uiState by vm.state.collectAsStateWithLifecycle()

                // Efectos (snackbar, navegación)
                LaunchedEffect(vm) {
                    vm.effects.collectLatest { effect ->
                        when (effect) {
                            is PhilosophyEffect.ShowSnackbar ->
                                snackbarHostState.showSnackbar(effect.text)
                            PhilosophyEffect.GoHome ->
                                navController.navigate(Destinations.Chat) {
                                    popUpTo(Destinations.Chat) { inclusive = true }
                                }
                        }
                    }
                }

                PhilosophiesScreen(
                    items = uiState.items,
                    selected = uiState.selected,
                    onToggle = { id -> vm.onEvent(PhilosophyEvent.Toggle(id)) },
                    onSave = { vm.onEvent(PhilosophyEvent.SaveClicked) }
                )
            }


            composable(Destinations.Chat) { backStackEntry ->
                val vm: HomeViewModel = hiltViewModel(backStackEntry)
                HomeScreen(homeViewModel = vm)
            }
        }
    }
}
