package com.example.launcher_app.presentation.navgraph

sealed class Route(
    val route: String
) {
    data object HomeScreen : Route("HomeScreen")
    data object RecentScreen : Route("RecentScreen")
    data object FolderScreen : Route("FolderScreen")
    data object FolderDetailScreen : Route("FolderDetailScreen")
}