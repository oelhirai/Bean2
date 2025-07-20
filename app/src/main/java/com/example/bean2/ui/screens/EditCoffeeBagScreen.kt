package com.example.bean2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bean2.data.model.BrewingParams
import com.example.bean2.data.model.CoffeeBag
import com.example.bean2.data.model.CoffeeType
import com.example.bean2.data.model.Dripper
import com.example.bean2.extensions.getGrind
import com.example.bean2.extensions.getRatio
import com.example.bean2.extensions.getTemperature
import com.example.bean2.ui.components.CoffeeTypeSelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCoffeeBagScreen(
    coffeeBag: CoffeeBag? = null,
    onBackClick: () -> Unit,
    onSaveClick: (String, String, CoffeeType, BrewingParams) -> Unit
) {
    val isEditMode = coffeeBag != null
    var name by remember { mutableStateOf(coffeeBag?.name.orEmpty()) }
    var roaster by remember { mutableStateOf(coffeeBag?.roaster.orEmpty()) }
    var selectedType by remember { mutableStateOf<CoffeeType?>(coffeeBag?.type) }

    // Brewing parameters state
    var temperature by remember { 
        mutableStateOf(coffeeBag?.getTemperature() ?: "")
    }
    var grindSize by remember { 
        mutableStateOf(coffeeBag?.getGrind() ?: "")
    }
    // Parse the existing ratio or default to 15
    val defaultRatio = coffeeBag?.getRatio()?.substringAfter(":")?.toIntOrNull() ?: 15
    var selectedRatio by remember { mutableStateOf(defaultRatio.coerceIn(1, 20)) }
    var isRatioExpanded by remember { mutableStateOf(false) }

    var dripper by remember { mutableStateOf((coffeeBag?.brewingParams as? BrewingParams.PourOverParams)?.dripper ?: Dripper.V60) }
    var yield by remember { mutableStateOf((coffeeBag?.brewingParams as? BrewingParams.EspressoParams)?.yield ?: "") }
    var extractionTime by remember { mutableStateOf((coffeeBag?.brewingParams as? BrewingParams.EspressoParams)?.extractionTime?.toString() ?: "") }
    var notes by remember { mutableStateOf(coffeeBag?.brewingParams?.notes ?: "") }
    var isDripperExpanded by remember { mutableStateOf(false) }

    val isFormValid = name.isNotBlank() &&
            roaster.isNotBlank() &&
            selectedType != null &&
            temperature.isNotBlank() &&
            grindSize.isNotBlank() &&
            selectedRatio in 1..20 &&
            (selectedType != CoffeeType.ESPRESSO || (yield.isNotBlank() && extractionTime.isNotBlank()))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Coffee Bag" else "Add Coffee Bag") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            selectedType?.let { type ->
                                val brewingParams = when (type) {
                                    CoffeeType.POUROVER -> BrewingParams.PourOverParams(
                                        temperature = temperature.toIntOrNull() ?: 0,
                                        grindSize = grindSize,
                                        ratio = "1:${selectedRatio}",
                                        dripper = dripper,
                                        notes = notes
                                    )

                                    CoffeeType.ESPRESSO -> BrewingParams.EspressoParams(
                                        temperature = temperature.toIntOrNull() ?: 0,
                                        grindSize = grindSize,
                                        ratio = "1:${selectedRatio}",
                                        yield = yield,
                                        extractionTime = extractionTime.toIntOrNull() ?: 0,
                                        notes = notes
                                    )
                                }
                                onSaveClick(name, roaster, type, brewingParams)
                                onBackClick()
                            }
                        },
                        enabled = isFormValid
                    ) {
                        Text("Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Coffee Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = roaster,
                onValueChange = { roaster = it },
                label = { Text("Roaster") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Brew Type",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            CoffeeTypeSelector(
                selectedType = selectedType,
                onTypeSelected = { selectedType = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Brewing Parameters Section
            Text(
                text = "Brewing Parameters",
                style = MaterialTheme.typography.titleMedium
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Temperature
                    OutlinedTextField(
                        value = temperature,
                        onValueChange = {
                            if (it.isEmpty() || it.toIntOrNull() != null) temperature = it
                        },
                        label = { Text("Temperature (°C)") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ), modifier = Modifier.fillMaxWidth()
                    )

                    // Grind Size
                    OutlinedTextField(
                        value = grindSize,
                        onValueChange = { grindSize = it },
                        label = { Text("Grind Size") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Ratio
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Coffee to Water Ratio",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "1:",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            ExposedDropdownMenuBox(
                                expanded = isRatioExpanded,
                                onExpandedChange = { isRatioExpanded = it },
                                modifier = Modifier.weight(1f)
                            ) {
                                TextField(
                                    value = selectedRatio.toString(),
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isRatioExpanded)
                                    },
                                    modifier = Modifier.menuAnchor()
                                )

                                ExposedDropdownMenu(
                                    expanded = isRatioExpanded,
                                    onDismissRequest = { isRatioExpanded = false }
                                ) {
                                    (1..20).forEach { number ->
                                        DropdownMenuItem(
                                            text = { Text(number.toString()) },
                                            onClick = {
                                                selectedRatio = number
                                                isRatioExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Dripper (only for pour over)
                    if (selectedType == CoffeeType.POUROVER) {
                        ExposedDropdownMenuBox(
                            expanded = isDripperExpanded,
                            onExpandedChange = { isDripperExpanded = it }
                        ) {
                            TextField(
                                value = dripper.name.replace("_", " "),
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Dripper") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDripperExpanded)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = isDripperExpanded,
                                onDismissRequest = { isDripperExpanded = false }
                            ) {
                                Dripper.values().forEach { dripperOption ->
                                    DropdownMenuItem(
                                        text = { Text(dripperOption.name.replace("_", " ")) },
                                        onClick = {
                                            dripper = dripperOption
                                            isDripperExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Additional fields for Espresso
                    if (selectedType == CoffeeType.ESPRESSO) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = yield,
                                onValueChange = { yield = it },
                                label = { Text("Yield (g)") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier.weight(1f)
                            )

                            OutlinedTextField(
                                value = extractionTime,
                                onValueChange = {
                                    if (it.isEmpty() || it.toIntOrNull() != null) extractionTime =
                                        it
                                },
                                label = { Text("Time (s)") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Notes
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notes") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )
                }
            }
        }
    }
}
