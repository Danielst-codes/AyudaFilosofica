package com.example.ayudafilosofica.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ayudafilosofica.feature.home.presentation.HomeEvent
import com.example.ayudafilosofica.feature.home.presentation.HomeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, homeViewModel: HomeViewModel) {
    //De este modo accedemos al valor en si del composable, ya que si solo accedemos al state,
    // seria como acceder a una tuberia en lugar de acceder a los elementos que pasan por esa tuberia
    val uiState = homeViewModel.state.collectAsStateWithLifecycle()
    val state = uiState.value
    //usamos esta lista solo para scroll, indice, nada de negocio
    val listState = rememberLazyListState()
    //Se utiliza para usar los efectos
    val launchedEfect = LaunchedEffect(key1 = homeViewModel.effects) {
        when (){

    }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn(
            state = listState ,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.messages, key = {it.id}){ menssage ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (menssage.deUsuario) Arrangement.End else Arrangement.Start
                    ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(0.85f ),
                        shape = RoundedCornerShape(18.dp),
                        tonalElevation = if (menssage.deUsuario) 2.dp else 0.dp,
                        color = if (menssage.deUsuario)
                                    MaterialTheme.colorScheme.primaryContainer
                                else
                                    MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            menssage.texto,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                //VEr la anotacion de arriba
                value = state.inputText,
                onValueChange = {  newText -> homeViewModel.onEvent(HomeEvent.InputChanged(newText))  },
                placeholder = { Text("Escribe tu mensaje…") },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        homeViewModel.onEvent(HomeEvent.SendClicked)
                    }
                ),
                enabled = !state.isSending
                ,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                )
            )

            FilledTonalIconButton(
                onClick = {
                    homeViewModel.onEvent(HomeEvent.SendClicked)
                },
                enabled = state.canSend  && !state.isSending
            ) {
                Icon(Icons.Default.Send, contentDescription = "Enviar")
            }
        }
    }
}



