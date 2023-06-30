package xyz.argent.candidateassessment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.argent.candidateassessment.ui.views.IntroRoute
import xyz.argent.candidateassessment.ui.views.tokens.TokensRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.IntroScreen,
        modifier = modifier
    ) {
        composable(NavRoutes.IntroScreen) {
            IntroRoute(
                onButtonClick = { navController.navigate(NavRoutes.TokensScreen) }
            )
        }

        composable(NavRoutes.TokensScreen) {
            TokensRoute(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
