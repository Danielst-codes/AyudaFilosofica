package com.example.ayudafilosofica.feature.auth.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ayudafilosofica.domain.Philosophy

@Composable
fun PhilosophiesScreen(
    items: List<Philosophy>,
    onToggle: (id: String, checked: Boolean) -> Unit
) {
    val selectCount = items.count { it.isSelected }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Aviso si hay más de 2 seleccionadas
            if (selectCount > 2) {
                WarningBanner(
                    message = "No siempre es mejor más: has seleccionado $selectCount filosofías."
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = items,
                    key = { it.id }
                ) { item ->
                    PhilosophyRow(
                        item = item,
                        onToggle = onToggle
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
private fun PhilosophyRow(
    item: Philosophy,
    onToggle: (String, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Switch(
            checked = item.isSelected,
            onCheckedChange = { checked -> onToggle(item.id, checked) }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun WarningBanner(message: String) {
    Surface(
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPhilosophyListScreen() {
    // Estado local solo para la preview
    val items = remember {
        mutableStateListOf(
            Philosophy("stoicism", "Estoicismo", false),
            Philosophy("epicurean", "Epicureísmo", true),
            Philosophy("aristotle", "Aristóteles", false)
        )
    }

    // Callback que actualiza la lista localmente
    val onToggle: (String, Boolean) -> Unit = { id, checked ->
        val idx = items.indexOfFirst { it.id == id }
        if (idx >= 0) {
            items[idx] = items[idx].copy(isSelected = checked)
        }
    }

    MaterialTheme {
        PhilosophiesScreen(
            items = items,
            onToggle = onToggle
        )
    }
}
