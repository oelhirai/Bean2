package com.example.bean2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bean2.data.model.BrewingParams.PourOverParams
import com.example.bean2.data.model.CoffeeBag
import com.example.bean2.data.model.CoffeeType
import com.example.bean2.data.model.Dripper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeBagItem(
    coffeeBag: CoffeeBag,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = coffeeBag.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = coffeeBag.roaster,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    coffeeBag.brewingParams?.let { brewingParams ->
                        Text(
                            text = brewingParams.grindSize,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${brewingParams.temperature}°C",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = brewingParams.ratio,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(
                            imageVector = when (coffeeBag.type) {
                                CoffeeType.ESPRESSO -> Icons.Default.AccountBox
                                CoffeeType.POUROVER -> Icons.Default.AccountCircle
                            },
                            contentDescription = coffeeBag.type.name,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            coffeeBag.notes.takeIf { it.isNotBlank() }?.let { notes ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = notes,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoffeeBagItemPreview() {
    val coffeeBag = CoffeeBag(
        id = "1",
        name = "Ethiopia Yirgacheffe",
        roaster = "Blue Bottle",
        type = CoffeeType.POUROVER,
        brewingParams = PourOverParams(
            dripper = Dripper.V60,
            grindSize = "5.8",
            temperature = 92,
            ratio = "1:16",
            notes = "Great fruity notes, 2:30 total brew time"
        )
    )

    CoffeeBagItem(
        coffeeBag = coffeeBag,
        onClick = {}
    )
}