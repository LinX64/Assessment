package xyz.argent.candidateassessment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import xyz.argent.candidateassessment.ui.navigation.AppNavHost
import xyz.argent.candidateassessment.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
           AppTheme {
               MyApp()
           }
        }
    }
}