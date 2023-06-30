package xyz.argent.candidateassessment.ui.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import xyz.argent.candidateassessment.ui.navigation.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    destination: NavDestination?
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Argent",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
        },
        modifier = modifier.fillMaxWidth(),
        navigationIcon = {
            if (destination?.route != NavRoutes.IntroScreen) {
                BackButton(onClick = { navController.popBackStack() })
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun BackButton(onClick: () -> Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { onClick() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
        Text(text = "Back")
    }
}