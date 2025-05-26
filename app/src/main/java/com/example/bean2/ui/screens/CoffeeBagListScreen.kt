package com.example.bean2.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bean2.R
import com.example.bean2.data.model.CoffeeType
import com.example.bean2.ui.components.CoffeeBagItem
import com.example.bean2.ui.viewmodel.CoffeeUiState
import com.example.bean2.ui.viewmodel.CoffeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeBagListScreen(
    viewModel: CoffeeViewModel,
    onCoffeeBagClick: (String) -> Unit,
    onAddClick: () -> Unit,
    onFilterClick: (CoffeeType?) -> Unit
) {
    var showFilterDialog by remember { mutableStateOf(false) }
    val selectedType by viewModel.selectedType.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coffee Journal") },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = "Filter",
                            tint = if (selectedType != null) MaterialTheme.colorScheme.primary 
                                 else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add, 
                    contentDescription = "Add Coffee"
                )
            }
        }
    ) { padding ->
        when (val state = uiState) {
            is CoffeeUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is CoffeeUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message, 
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            is CoffeeUiState.Success -> {
                if (state.coffeeBags.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No coffee bags found")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        items(state.coffeeBags) { coffeeBag ->
                            CoffeeBagItem(
                                coffeeBag = coffeeBag,
                                onClick = { onCoffeeBagClick(coffeeBag.id) }
                            )
                            Divider()
                        }
                    }
                }
            }
        }
    }

    if (showFilterDialog) {
        FilterDialog(
            selectedType = selectedType,
            onDismiss = { showFilterDialog = false },
            onConfirm = { type ->
                onFilterClick(type)
                showFilterDialog = false
            }
        )
    }
}



@Composable
private fun FilterDialog(
    selectedType: CoffeeType?,
    onDismiss: () -> Unit,
    onConfirm: (CoffeeType?) -> Unit
) {
    var tempSelectedType by remember { mutableStateOf(selectedType) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filter by type") },
        text = {
            Column {
                RadioButtonItem(
                    text = "All",
                    selected = tempSelectedType == null,
                    onSelect = { tempSelectedType = null }
                )
                CoffeeType.values().forEach { type ->
                    RadioButtonItem(
                        text = type.name,
                        selected = tempSelectedType == type,
                        onSelect = { tempSelectedType = type }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(tempSelectedType) }) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun RadioButtonItem(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onSelect
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
