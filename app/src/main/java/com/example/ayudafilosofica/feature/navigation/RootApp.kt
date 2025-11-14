package com.example.ayudafilosofica.feature.navigation

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
import com.example.ayudafilosofica.feature.auth.presentation.PhilosophyEvent
import com.example.ayudafilosofica.feature.auth.presentation.PhilosophyViewModel
import com.example.ayudafilosofica.feature.auth.presentation.PhilosophyEffect
import com.example.ayudafilosofica.feature.auth.ui.components.PhilosophiesScreen
import com.example.ayudafilosofica.feature.home.presentation.HomeViewModel
import com.example.ayudafilosofica.feature.home.ui.HomeScreen
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.collections.get

private val mapaTitulos = mapOf(
    Destinations.Chat to "Chat",
    Destinations.Philosophies to "Filosofías",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootApp() {
    val navController = rememberNavController()

    // ✅ Forma correcta de observar el back stack
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val tituloBarra = mapaTitulos[currentRoute] ?: "Ayuda Filosófica"

    var menuAbierto by remember { mutableStateOf(false) }

    fun navTo(route: String) {
        menuAbierto = false

        val yaEstamos = currentDestination
            ?.hierarchy
            ?.any { it.route == route } == true

        if (yaEstamos) return   // ya estamos ahí, no navego

        // ❗ NAVEGACIÓN SIMPLE. Sin popUpTo ni restoreState, para no liarla.
        navController.navigate(route) {
            launchSingleTop = true
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
                        DropdownMenu(
                            expanded = menuAbierto,
                            onDismissRequest = { menuAbierto = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Chat") },
                                onClick = { navTo(Destinations.Chat) }
                            )
                            DropdownMenuItem(
                                text = { Text("Filosofías") },
                                onClick = { navTo(Destinations.Philosophies) }
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.Philosophies,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Destinations.Philosophies) { backStackEntry ->
                val vm: PhilosophyViewModel = hiltViewModel(backStackEntry)
                val uiState by vm.state.collectAsStateWithLifecycle()

                LaunchedEffect(vm) {
                    vm.effects.collectLatest { effect ->
                        when (effect) {
                            is PhilosophyEffect.ShowSnackbar ->
                                snackbarHostState.showSnackbar(effect.text)

                            PhilosophyEffect.GoHome ->
                                // ▶️ Simple: vamos al Chat
                                navController.navigate(Destinations.Chat) {
                                    launchSingleTop = true
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
