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
import com.example.ayudafilosofica.domain.phylosophy.model.Philosophy


@Composable
fun PhilosophiesScreen(
    items: List<Philosophy>,
    selected: Set<String>,
    onToggle: (id: String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Aviso simple si hay más de 2 seleccionadas
        if (selected.size > 2) {
            WarningBanner(
                message = "Has seleccionado ${selected.size}. Combinar muchas filosofías puede diluir el consejo."
            )
            Spacer(Modifier.height(8.dp))
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items, key = { it.id }) { item ->
                PhilosophyRow(
                    item = item,
                    isSelected = item.id in selected,
                    onToggle = { onToggle(item.id) }
                )
                Divider()
            }
        }

        Button(
            onClick = onSave,
            enabled = selected.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text("Guardar")
        }
    }
}

@Composable
private fun PhilosophyRow(
    item: Philosophy,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = isSelected,
            onCheckedChange = { onToggle() }
        )
        Spacer(Modifier.width(12.dp))
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
        modifier = Modifier.fillMaxWidth()
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
private fun PreviewPhilosophiesScreen() {
    val items = listOf(
        Philosophy("stoicism", "Estoicismo"),
        Philosophy("existentialism", "Existencialismo"),
        Philosophy("aristotelian", "Aristotelismo")
    )
    var selected by remember { mutableStateOf(setOf("existentialism")) }

    MaterialTheme {
        PhilosophiesScreen(
            items = items,
            selected = selected,
            onToggle = { id ->
                selected = if (id in selected) selected - id else selected + id
            },
            onSave = { /* preview */ }
        )
    }
}
