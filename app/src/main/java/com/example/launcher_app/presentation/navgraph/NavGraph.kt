package com.example.launcher_app.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.launcher_app.domain.model.Folder
import com.example.launcher_app.helper.Constant
import com.example.launcher_app.presentation.folder.FolderDetail
import com.example.launcher_app.presentation.folder.FolderScreen
import com.example.launcher_app.presentation.home.HomeScreen
import com.example.launcher_app.presentation.recent.RecentScreen

@Composable
fun NavGraph(
    destination: String
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        composable(
            route = Route.HomeScreen.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Route.RecentScreen.route
        ) {
            RecentScreen(navController = navController)
        }
        composable(
            route = Route.FolderScreen.route
        ) {
            FolderScreen(navController = navController)
        }
        composable(
            route = Route.FolderDetailScreen.route
        ) {
            navController.previousBackStackEntry?.savedStateHandle?.get<Folder?>(Constant.FOLDER)
                ?.let { folder ->
                    FolderDetail(
                        folder = folder,
                        navController = navController
                    )
                }
        }
    }
}