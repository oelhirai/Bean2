package com.example.bean2.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bean2.data.model.CoffeeType

@Preview(showBackground = true)
@Composable
fun AddCoffeeBagScreenPreview() {
    AddCoffeeBagScreen(
        onBackClick = {},
        onSaveClick = { _, _, _, _ -> }
    )
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddCoffeeBagScreenDarkPreview() {
    AddCoffeeBagScreen(
        onBackClick = {},
        onSaveClick = { _, _, _, _ -> }
    )
}
