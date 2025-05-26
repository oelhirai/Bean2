package com.example.bean2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bean2.data.model.CoffeeType

@Composable
fun CoffeeTypeSelector(
    selectedType: CoffeeType?,
    onTypeSelected: (CoffeeType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CoffeeType.values().forEach { type ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (type == selectedType),
                        onClick = { onTypeSelected(type) }
                    )
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = (type == selectedType),
                    onClick = { onTypeSelected(type) }
                )
                Text(
                    text = when (type) {
                        CoffeeType.POUROVER -> "Pour Over"
                        CoffeeType.ESPRESSO -> "Espresso"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
