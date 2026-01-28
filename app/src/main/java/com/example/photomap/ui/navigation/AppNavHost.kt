package com.example.photomap.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.photomap.AppContainer
import com.example.photomap.ui.screens.DetailsScreen
import com.example.photomap.ui.screens.MapScreen
import com.example.photomap.ui.viewmodel.DetailsViewModel
import com.example.photomap.ui.viewmodel.MapViewModel
import com.example.photomap.ui.viewmodel.ViewModelFactories

@Composable
fun AppNavHost(appContainer: AppContainer) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val factories = remember(appContainer) {
        ViewModelFactories(
            appContext = context.applicationContext,
            photoRepo = appContainer.photoPointRepository,
            locationRepo = appContainer.locationRepository
        )
    }

    NavHost(
        navController = navController,
        startDestination = Route.Map.path
    ) {
        composable(Route.Map.path) {
            val vm: MapViewModel = viewModel(factory = factories.mapFactory)
            MapScreen(
                viewModel = vm,
                onOpenDetails = { id -> navController.navigate(Route.Details.create(id)) }
            )
        }

        composable(
            Route.Details.path,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStack ->
            val id = backStack.arguments!!.getLong("id")
            val vm: DetailsViewModel = viewModel(factory = factories.detailsFactory(id))
            DetailsScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
