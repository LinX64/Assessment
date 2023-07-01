package xyz.argent.candidateassessment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.argent.candidateassessment.ui.views.IntroRoute
import xyz.argent.candidateassessment.ui.views.tokens.TokensRoute

@Composable
internal fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.IntroScreen,
        modifier = modifier
    ) {
        introScreen(navController)
        tokensScreen(navController)
    }
}

private fun NavGraphBuilder.introScreen(navController: NavHostController) {
    composable(NavRoutes.IntroScreen) {
        IntroRoute(
            onButtonClick = { navController.navigate(NavRoutes.TokensScreen) }
        )
    }
}

private fun NavGraphBuilder.tokensScreen(navController: NavHostController) {
    composable(NavRoutes.TokensScreen) {
        TokensRoute(
            onBackClick = { navController.popBackStack() }
        )
    }
}
