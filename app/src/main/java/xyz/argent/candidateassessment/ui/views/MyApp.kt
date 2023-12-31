package xyz.argent.candidateassessment.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import xyz.argent.candidateassessment.ui.navigation.AppNavHost
import xyz.argent.candidateassessment.ui.navigation.AppState
import xyz.argent.candidateassessment.ui.navigation.NavRoutes
import xyz.argent.candidateassessment.ui.navigation.rememberAppState
import xyz.argent.candidateassessment.ui.views.components.TopAppBar

@Composable
fun MyApp(
    appState: AppState = rememberAppState()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val currentDestination = appState.currentDestination

    Scaffold(
        topBar = { MyAppBar(currentDestination, appState) },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(Modifier.fillMaxSize()) {
            AppNavigation(
                navController = appState.navController,
                padding = padding
            )
        }
    }
}

@Composable
private fun MyAppBar(
    currentDestination: NavDestination?,
    appState: AppState
) {
    if (currentDestination?.route != NavRoutes.TokensScreen) {
        TopAppBar(
            navController = appState.navController,
            destination = currentDestination
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AppNavigation(
    navController: NavHostController,
    padding: PaddingValues
) {
    AppNavHost(
        navController = navController,
        modifier = Modifier
            .padding(padding)
            .consumeWindowInsets(padding)
    )
}
