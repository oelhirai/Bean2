package com.example.bean2.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bean2.data.model.BrewingParams
import com.example.bean2.data.model.CoffeeBag
import com.example.bean2.data.model.CoffeeType
import com.example.bean2.ui.screens.AddCoffeeBagScreen
import com.example.bean2.ui.screens.CoffeeBagDetailScreen
import com.example.bean2.ui.screens.CoffeeBagListScreen
import com.example.bean2.ui.viewmodel.CoffeeViewModel

sealed class Screen(val route: String) {
    object CoffeeBagList : Screen("coffee_bag_list")
    object CoffeeBagDetail : Screen("coffee_bag_detail/{coffeeBagId}") {
        fun createRoute(coffeeBagId: String) = "coffee_bag_detail/$coffeeBagId"
    }
    object AddCoffeeBag : Screen("add_coffee_bag")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: CoffeeViewModel = hiltViewModel(),
    startDestination: String = Screen.CoffeeBagList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.CoffeeBagList.route) {
            val uiState by viewModel.uiState.collectAsState()
            val selectedType by viewModel.selectedType.collectAsState()
            
            CoffeeBagListScreen(
                viewModel = viewModel,
                onCoffeeBagClick = { coffeeBagId ->
                    navController.navigate(Screen.CoffeeBagDetail.createRoute(coffeeBagId))
                },
                onAddClick = {
                    navController.navigate(Screen.AddCoffeeBag.route)
                },
                onFilterClick = { type ->
                    viewModel.setSelectedType(type)
                }
            )
        }
        
        composable(Screen.CoffeeBagDetail.route) { backStackEntry ->
            val coffeeBagId = backStackEntry.arguments?.getString("coffeeBagId")
            LaunchedEffect(coffeeBagId) {
                coffeeBagId?.let { viewModel.selectCoffeeBag(it) }
            }
            
            val coffeeBag by viewModel.selectedCoffeeBag.collectAsState()
            
            coffeeBag?.let { bag ->
                CoffeeBagDetailScreen(
                    coffeeBag = bag,
                    onBackClick = { navController.navigateUp() },
                    onEditClick = { /* TODO: Implement edit */ },
                    onDeleteClick = {
                        viewModel.deleteCoffeeBag(bag.id)
                        navController.navigateUp()
                    }
                )
            } ?: run {
                // Show loading or error state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        
        composable(Screen.AddCoffeeBag.route) {
            AddCoffeeBagScreen(
                onBackClick = { navController.navigateUp() },
                onSaveClick = { name: String, roaster: String, type: CoffeeType, brewingParams: BrewingParams ->
                    viewModel.addCoffeeBag(
                        CoffeeBag(
                            name = name,
                            roaster = roaster,
                            type = type,
                            brewingParams = brewingParams
                        )
                    )
                    navController.navigateUp()
                }
            )
        }
    }
}
