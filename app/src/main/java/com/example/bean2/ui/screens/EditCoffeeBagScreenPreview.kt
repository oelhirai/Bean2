package com.example.bean2.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bean2.data.model.BrewingParams
import com.example.bean2.data.model.CoffeeBag
import com.example.bean2.data.model.CoffeeType
import com.example.bean2.data.model.Dripper

@Preview(showBackground = true)
@Composable
fun AddCoffeeBagScreenPreview() {
    EditCoffeeBagScreen(
        coffeeBag = null,
        onBackClick = {},
        onSaveClick = { _, _, _, _ -> }
    )
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EditCoffeeBagScreenPreview() {
    val sampleCoffeeBag = CoffeeBag(
        id = "1",
        name = "Ethiopian Yirgacheffe",
        roaster = "Blue Bottle",
        type = CoffeeType.POUROVER,
        brewingParams = BrewingParams.PourOverParams(
            temperature = 96,
            grindSize = "Medium-Fine",
            ratio = "1:15",
            dripper = Dripper.V60,
            notes = "Fruity and floral with notes of blueberry and jasmine."
        )
    )
    
    EditCoffeeBagScreen(
        coffeeBag = sampleCoffeeBag,
        onBackClick = {},
        onSaveClick = { _, _, _, _ -> }
    )
}
